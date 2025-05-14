package br.com.fiap.medhora.service.impl;

import br.com.fiap.medhora.application.request.AgendamentoRequest;
import br.com.fiap.medhora.application.response.ConsultasResponse;
import br.com.fiap.medhora.application.MensagemResponse;
import br.com.fiap.medhora.exception.ErroInternoException;
import br.com.fiap.medhora.exception.agendamento.AgendamentoExistsException;
import br.com.fiap.medhora.exception.agendamento.AgendamentoNotExistsException;
import br.com.fiap.medhora.kafka.dto.NotificacaoConsultaDTO;
import br.com.fiap.medhora.kafka.producer.ConsultaProducerService;
import br.com.fiap.medhora.mapper.AgendamentoMapper;
import br.com.fiap.medhora.model.AgendamentoEntity;
import br.com.fiap.medhora.repository.AgendamentoRepository;
import br.com.fiap.medhora.service.port.AgendamentoServicePort;
import br.com.fiap.medhora.utils.ConstantUtils;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
public class AgendamentoServiceImpl implements AgendamentoServicePort {


    @Autowired
    private AgendamentoRepository repository;

    @Autowired
    private ConsultaProducerService consultaProducerService;

    @Transactional
    @Override
    public MensagemResponse cadastrarConsulta(AgendamentoRequest consulta) {

        if (repository.existsByNomePacienteAndMotivoConsulta(consulta.getNomePaciente(), consulta.getMotivoConsulta())) {
            throw new AgendamentoExistsException(ConstantUtils.CONSULTA_EXISTENTE);
        }

        try {
            AgendamentoEntity entity = AgendamentoMapper.INSTANCE.requestToEntity(consulta);
            repository.save(entity);

            // Cria o DTO para enviar para o Kafka

            String formattedDate = consulta.getDataHoraConsulta().format(DateTimeFormatter.ISO_DATE_TIME);

            NotificacaoConsultaDTO dto = new NotificacaoConsultaDTO();
            dto.setNomePaciente(consulta.getNomePaciente());
            dto.setDataHoraConsulta(formattedDate);
            dto.setMotivoConsulta(consulta.getMotivoConsulta());

            // Envia a mensagem para o Kafka
            consultaProducerService.enviarMensagem(dto);

            return new MensagemResponse(ConstantUtils.CONSULTA_CADASTRADA);
        } catch (Exception e) {
            log.error("Erro ao cadastrar consulta", e);
            throw new ErroInternoException("Erro ao cadastrar consulta: " + e.getMessage());
        }
    }

    @Override
    public List<ConsultasResponse> listarTodasConsultas() {
        List<AgendamentoEntity> consultas = repository.findAll();
        return AgendamentoMapper.INSTANCE.entitysToResponses(consultas);
    }

    @Override
    public ConsultasResponse buscarConsulta(Integer id) {

        AgendamentoEntity entity = repository.findById(id)
                .orElseThrow(() -> new AgendamentoNotExistsException(ConstantUtils.AGENDAMENTO_NAO_ENCONTRADO));

        return AgendamentoMapper.INSTANCE.entityToResponse(entity);
    }

    @Transactional
    @Override
    public MensagemResponse atualizarConsulta(AgendamentoRequest consulta, Integer id) {
        repository.findById(id)
                .orElseThrow(() -> new AgendamentoNotExistsException(ConstantUtils.AGENDAMENTO_NAO_ENCONTRADO));

        try {
            AgendamentoEntity entity = AgendamentoMapper.INSTANCE.requestToEntity(consulta);
            entity.setIdConsulta(id);
            repository.save(entity);

            // Cria o DTO para enviar para o Kafka (Notificação)

            String formattedDate = consulta.getDataHoraConsulta().format(DateTimeFormatter.ISO_DATE_TIME);

            NotificacaoConsultaDTO dto = new NotificacaoConsultaDTO();
            dto.setNomePaciente(consulta.getNomePaciente());
            dto.setDataHoraConsulta(formattedDate);
            dto.setMotivoConsulta(consulta.getMotivoConsulta());


            // Envia a mensagem para o Kafka (Serviço de Notificação)
            consultaProducerService.enviarMensagem(dto);

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
