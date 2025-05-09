package br.com.fiap.medhora.exception.agendamento;

public class AgendamentoNotExistsException extends RuntimeException {
    public AgendamentoNotExistsException(String message) {
        super(message);
    }
}
