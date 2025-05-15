package br.com.fiap.medhora.kafka.producer.impl;

import br.com.fiap.medhora.kafka.dto.NotificacaoConsultaDTO;
import br.com.fiap.medhora.kafka.producer.port.ConsultaProducerServicePort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ConsultaProducerServiceImpl implements ConsultaProducerServicePort {

    private static final String TOPICO = "notificacoes-consulta";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public ConsultaProducerServiceImpl(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void enviarMensagem(NotificacaoConsultaDTO dto) {
        kafkaTemplate.send(TOPICO, dto);
        log.info("Mensagem enviada para o tópico {}: {}", TOPICO, dto);
    }
}