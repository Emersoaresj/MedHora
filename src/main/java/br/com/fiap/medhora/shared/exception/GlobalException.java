package br.com.fiap.medhora.shared.exception;

import br.com.fiap.medhora.shared.exception.agendamento.AgendamentoExistsException;
import br.com.fiap.medhora.shared.exception.agendamento.AgendamentoNotExistsException;
import br.com.fiap.medhora.shared.exception.notificacao.ErroConsumerKafkaException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalException {


    private static final String TIMESTAMP = "timestamp";
    private static final String STATUS = "status";
    private static final String ERROR = "error";
    private static final String MENSAGEM = "mensagem";
    private static final String PATH = "path";



    @ExceptionHandler(ErroInternoException.class)
    public ResponseEntity<Map<String, Object>> handlerErroBancoDeDados(ErroInternoException erroInternoException, WebRequest request) {
        Map<String, Object> response = new HashMap<>();
        response.put(TIMESTAMP, LocalDateTime.now());
        response.put(STATUS, HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.put(ERROR, "Erro ao salvar no Banco de Dados.");
        response.put(MENSAGEM, erroInternoException.getMessage());
        response.put(PATH, request.getDescription(false));

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(AgendamentoExistsException.class)
    public ResponseEntity<Map<String, Object>> handlerAgendamentoExists(AgendamentoExistsException agendamentoExistsException) {
        Map<String, Object> response = new HashMap<>();
        response.put(MENSAGEM, agendamentoExistsException.getMessage());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AgendamentoNotExistsException.class)
    public ResponseEntity<Map<String, Object>> handlerAgendamentoNotExists(AgendamentoNotExistsException agendamentoNotExistsException) {
        Map<String, Object> response = new HashMap<>();
        response.put(MENSAGEM, agendamentoNotExistsException.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ErroConsumerKafkaException.class)
    public ResponseEntity<Map<String, Object>> handlerErroConsumerKafka(ErroConsumerKafkaException erroConsumerKafkaException) {
        Map<String, Object> response = new HashMap<>();
        response.put(MENSAGEM, erroConsumerKafkaException.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }



}
