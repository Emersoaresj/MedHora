package br.com.fiap.medhora.exception.notificacao;

public class ErroConsumerKafkaException extends RuntimeException {
    public ErroConsumerKafkaException(String message) {
        super(message);
    }
}
