package br.com.fiap.medhora.shared.exception.agendamento;

public class AgendamentoNotExistsException extends RuntimeException {
    public AgendamentoNotExistsException(String message) {
        super(message);
    }
}
