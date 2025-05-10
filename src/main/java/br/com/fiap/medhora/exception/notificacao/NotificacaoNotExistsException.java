package br.com.fiap.medhora.exception.notificacao;

public class NotificacaoNotExistsException extends RuntimeException {
    public NotificacaoNotExistsException(String message) {
        super(message);
    }
}
