package br.com.fiap.medhora.exception.notificacao;

public class NotificacaoExistsException extends RuntimeException {
    public NotificacaoExistsException(String message) {
        super(message);
    }
}
