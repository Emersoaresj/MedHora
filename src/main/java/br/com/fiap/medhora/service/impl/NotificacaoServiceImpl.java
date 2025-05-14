package br.com.fiap.medhora.service.impl;

import br.com.fiap.medhora.kafka.dto.NotificacaoConsultaDTO;
import br.com.fiap.medhora.model.NotificacaoEntity;
import br.com.fiap.medhora.repository.NotificacaoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
public class NotificacaoServiceImpl {

    @Autowired
    private NotificacaoRepository repository;

    @KafkaListener(topics = "notificacoes-consulta", groupId = "notificacao-group")
    public void processarNotificacao(NotificacaoConsultaDTO notificacaoDTO) {
        try {
            // Cria a entidade Notificacao para persistir no banco
            NotificacaoEntity entity = new NotificacaoEntity();
            entity.setNomePaciente(notificacaoDTO.getNomePaciente());
            entity.setMotivoConsulta(notificacaoDTO.getMotivoConsulta());

            entity.setDataHoraConsulta(LocalDateTime.parse(notificacaoDTO.getDataHoraConsulta(), DateTimeFormatter.ISO_DATE_TIME));

            entity.setStatus("ENVIADO");  // Status da notificação
            entity.setDataEnvioLembrete(LocalDateTime.now());  // Data do envio

            // Persiste no banco de dados
            repository.save(entity);

            log.info("Notificação enviada ao paciente: {}", notificacaoDTO.getNomePaciente());
        } catch (Exception e) {
            log.error("Erro ao processar a notificação", e);
        }
    }

}
