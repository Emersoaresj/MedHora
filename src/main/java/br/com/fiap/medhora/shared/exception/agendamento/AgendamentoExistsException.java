package br.com.fiap.medhora.shared.exception.agendamento;

public class AgendamentoExistsException extends RuntimeException {
    public AgendamentoExistsException(String message) {
        super(message);
    }
}
