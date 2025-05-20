package br.com.fiap.medhora.graphql.service.impl;

import br.com.fiap.medhora.exception.agendamento.AgendamentoNotExistsException;
import br.com.fiap.medhora.graphql.dto.AgendamentoDTO;
import br.com.fiap.medhora.graphql.service.port.AgendamentoGraphQLServicePort;
import br.com.fiap.medhora.model.AgendamentoEntity;
import br.com.fiap.medhora.repository.AgendamentoRepository;
import br.com.fiap.medhora.utils.ConstantUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AgendamentoGraphQLServiceImpl implements AgendamentoGraphQLServicePort {

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Override
    public AgendamentoDTO buscarPorId(Integer idConsulta) {

        AgendamentoEntity entity = agendamentoRepository.findById(idConsulta)
                .orElseThrow(() -> new AgendamentoNotExistsException(ConstantUtils.AGENDAMENTO_NAO_ENCONTRADO));

        return toDTO(entity);
    }


    @Override
    public List<AgendamentoDTO> buscarTodosOuPorPaciente(String nomePaciente, boolean somenteFuturos) {
        List<AgendamentoEntity> agendamentos;

        if (nomePaciente != null && !nomePaciente.isBlank()) {
            agendamentos = somenteFuturos
                    ? agendamentoRepository.findByNomePacienteAndDataHoraConsultaAfter(nomePaciente, LocalDateTime.now())
                    : agendamentoRepository.findByNomePaciente(nomePaciente);
        } else {
            agendamentos = somenteFuturos
                    ? agendamentoRepository.findByDataHoraConsultaAfter(LocalDateTime.now())
                    : agendamentoRepository.findAll();
        }

        return agendamentos.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private AgendamentoDTO toDTO(AgendamentoEntity entity) {
        AgendamentoDTO dto = new AgendamentoDTO();
        dto.setId(entity.getIdConsulta());
        dto.setDataHora(entity.getDataHoraConsulta());
        dto.setDescricao(entity.getMotivoConsulta());
        dto.setPaciente(entity.getNomePaciente());
        dto.setMedico(entity.getNomeMedico());
        return dto;
    }
}


