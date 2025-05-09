package br.com.fiap.medhora.exception.agendamento;

public class AgendamentoExistsException extends RuntimeException {
    public AgendamentoExistsException(String message) {
        super(message);
    }
}
