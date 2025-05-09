package br.com.fiap.medhora.service.impl;

import br.com.fiap.medhora.application.request.AgendamentoRequest;
import br.com.fiap.medhora.application.response.ConsultasResponse;
import br.com.fiap.medhora.application.MensagemResponse;
import br.com.fiap.medhora.exception.ErroInternoException;
import br.com.fiap.medhora.exception.agendamento.AgendamentoExistsException;
import br.com.fiap.medhora.exception.agendamento.AgendamentoNotExistsException;
import br.com.fiap.medhora.mapper.AgendamentoMapper;
import br.com.fiap.medhora.model.AgendamentoEntity;
import br.com.fiap.medhora.repository.AgendamentoRepository;
import br.com.fiap.medhora.service.port.AgendamentoServicePort;
import br.com.fiap.medhora.utils.ConstantUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AgendamentoServiceImpl implements AgendamentoServicePort {


    @Autowired
    private AgendamentoRepository repository;

    @Override
    public MensagemResponse cadastrarConsulta(AgendamentoRequest consulta) {

        if (repository.existsByNomePacienteAndMotivoConsulta(consulta.getNomePaciente(), consulta.getMotivoConsulta())) {
            throw new AgendamentoExistsException(ConstantUtils.CONSULTA_EXISTENTE);
        }

        try {
            AgendamentoEntity entity = AgendamentoMapper.INSTANCE.requestToEntity(consulta);
            repository.save(entity);
            return new MensagemResponse(ConstantUtils.CONSULTA_CADASTRADA);
        } catch (Exception e) {
            log.error("Erro ao cadastrar consulta", e);
            throw new ErroInternoException("Erro ao cadastrar consulta: " + e.getMessage());
        }
    }

    @Override
    public List<ConsultasResponse> listarTodasConsultas() {
        List<AgendamentoEntity> consultas = repository.findAll();
        return AgendamentoMapper.INSTANCE.entityToResponse(consultas);
    }

    @Override
    public ConsultasResponse buscarConsulta(Integer id) {

        AgendamentoEntity entity = repository.findById(id)
                .orElseThrow(() -> new AgendamentoNotExistsException(ConstantUtils.AGENDAMENTO_NAO_ENCONTRADO));

        return AgendamentoMapper.INSTANCE.entityToResponse(entity);
    }

    @Override
    public MensagemResponse atualizarConsulta(AgendamentoRequest consulta, Integer id) {
        repository.findById(id)
                .orElseThrow(() -> new AgendamentoNotExistsException(ConstantUtils.AGENDAMENTO_NAO_ENCONTRADO));

        try {
            AgendamentoEntity entity = AgendamentoMapper.INSTANCE.requestToEntity(consulta);
            entity.setIdConsulta(id);
            repository.save(entity);
            return new MensagemResponse(ConstantUtils.CONSULTA_ATUALIZADA);
        } catch (Exception e) {
            log.error("Erro ao atualizar consulta", e);
            throw new ErroInternoException("Erro ao atualizar consulta: " + e.getMessage());
        }
    }

    @Override
    public MensagemResponse deletar(Integer id) {
        repository.findById(id)
                .orElseThrow(() -> new AgendamentoNotExistsException(ConstantUtils.AGENDAMENTO_NAO_ENCONTRADO));
        try {
            repository.deleteById(id);
            return new MensagemResponse(ConstantUtils.CONSULTA_DELETADA);
        } catch (Exception e) {
            log.error("Erro ao deletar cardápio", e);
            throw new ErroInternoException("Erro ao deletar cardápio: " + e.getMessage());
        }
    }


}
