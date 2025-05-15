package br.com.fiap.medhora.kafka.producer.port;

import br.com.fiap.medhora.kafka.dto.NotificacaoConsultaDTO;

public interface ConsultaProducerServicePort {

    void enviarMensagem(NotificacaoConsultaDTO dto);
}
