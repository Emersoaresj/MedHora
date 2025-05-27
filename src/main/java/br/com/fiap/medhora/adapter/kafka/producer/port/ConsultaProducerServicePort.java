package br.com.fiap.medhora.adapter.kafka.producer.port;

import br.com.fiap.medhora.adapter.kafka.dto.NotificacaoConsultaDTO;

public interface ConsultaProducerServicePort {

    void enviarMensagem(NotificacaoConsultaDTO dto);
}
