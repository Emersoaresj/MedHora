package br.com.fiap.medhora.kafka.consumer;

import br.com.fiap.medhora.exception.notificacao.ErroConsumerKafkaException;
import br.com.fiap.medhora.kafka.dto.NotificacaoConsultaDTO;
import br.com.fiap.medhora.mapper.NotificacaoMapper;
import br.com.fiap.medhora.model.NotificacaoEntity;
import br.com.fiap.medhora.repository.NotificacaoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Slf4j
@Service
public class NotificacaoConsumer {

    private static final String TOPICO = "notificacoes-consulta";
    private static final String GRUPO = "notificacao-group";

    @Autowired
    private NotificacaoRepository repository;


    @KafkaListener(topics = TOPICO, groupId = GRUPO, containerFactory = "kafkaListenerContainerFactory")
    public void processarNotificacao(NotificacaoConsultaDTO notificacaoDTO) {

        log.info("Mensagem recebida no tópico: {}", notificacaoDTO);
        try {
            NotificacaoEntity entity = NotificacaoMapper.INSTANCE.kafkaDtoToEntity(notificacaoDTO);
            entity.setDataHoraConsulta(LocalDateTime.parse(notificacaoDTO.getDataHoraConsulta()));
            entity.setStatus("ENVIADO");
            entity.setDataEnvioLembrete(ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).toLocalDateTime());
            repository.save(entity);
            log.info("Notificação enviada ao paciente: {}", notificacaoDTO.getNomePaciente());
        } catch (Exception e) {
            log.error("Erro ao consumir o tópico no kafka: {}", e.getMessage());
            throw new ErroConsumerKafkaException("Erro ao processar a notificação: " + e.getMessage());
        }
    }


}
