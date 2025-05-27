package br.com.fiap.medhora.controller;

import br.com.fiap.medhora.adapter.controller.AgendamentoController;
import br.com.fiap.medhora.application.MensagemResponse;
import br.com.fiap.medhora.application.request.AgendamentoRequest;
import br.com.fiap.medhora.application.response.ConsultasResponse;
import br.com.fiap.medhora.application.service.port.AgendamentoServicePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class AgendamentoControllerTest {

    @InjectMocks
    private AgendamentoController agendamentoController;

    @Mock
    private AgendamentoServicePort consultaService;

    @Test
    void cadastrarConsultaReturnsCreatedWhenSuccessful() {
        AgendamentoRequest request = new AgendamentoRequest();
        MensagemResponse response = new MensagemResponse("Consulta cadastrada com sucesso!");
        when(consultaService.cadastrarConsulta(request)).thenReturn(response);

        ResponseEntity<MensagemResponse> result = agendamentoController.cadastrarConsulta(request);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    void cadastrarConsultaReturnsConflictWhenConsultaAlreadyExists() {
        AgendamentoRequest request = new AgendamentoRequest();
        when(consultaService.cadastrarConsulta(request))
                .thenThrow(new IllegalArgumentException("Consulta já existe para este paciente."));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            agendamentoController.cadastrarConsulta(request);
        });

        assertEquals("Consulta já existe para este paciente.", exception.getMessage());
    }

    @Test
    void cadastrarConsultaReturnsInternalServerErrorOnUnexpectedError() {
        AgendamentoRequest request = new AgendamentoRequest();
        when(consultaService.cadastrarConsulta(request))
                .thenThrow(new RuntimeException("Erro interno ao cadastrar consulta."));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            agendamentoController.cadastrarConsulta(request);
        });

        assertEquals("Erro interno ao cadastrar consulta.", exception.getMessage());
    }

    @Test
    void listarTodasConsultasReturnsOkWhenSuccessful() {
        List<ConsultasResponse> consultas = List.of(new ConsultasResponse());
        when(consultaService.listarTodasConsultas()).thenReturn(consultas);

        ResponseEntity<List<ConsultasResponse>> result = agendamentoController.listarTodasConsultas();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(consultas, result.getBody());
    }

    @Test
    void listarTodasConsultasReturnsInternalServerErrorOnUnexpectedError() {
        when(consultaService.listarTodasConsultas()).thenThrow(new RuntimeException("Erro interno ao listar consultas."));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            agendamentoController.listarTodasConsultas();
        });

        assertEquals("Erro interno ao listar consultas.", exception.getMessage());
    }

    @Test
    void buscarConsultaReturnsOkWhenConsultaExists() {
        int consultaId = 1;
        ConsultasResponse consultaResponse = new ConsultasResponse();
        when(consultaService.buscarConsulta(consultaId)).thenReturn(consultaResponse);

        ResponseEntity<ConsultasResponse> result = agendamentoController.buscarConsulta(consultaId);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(consultaResponse, result.getBody());
    }

    @Test
    void buscarConsultaReturnsNotFoundWhenConsultaDoesNotExist() {
        int consultaId = 1;
        when(consultaService.buscarConsulta(consultaId))
                .thenThrow(new IllegalArgumentException("Consulta não encontrada."));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            agendamentoController.buscarConsulta(consultaId);
        });

        assertEquals("Consulta não encontrada.", exception.getMessage());
    }

    @Test
    void buscarConsultaReturnsInternalServerErrorOnUnexpectedError() {
        int consultaId = 1;
        when(consultaService.buscarConsulta(consultaId))
                .thenThrow(new RuntimeException("Erro interno ao buscar consulta."));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            agendamentoController.buscarConsulta(consultaId);
        });

        assertEquals("Erro interno ao buscar consulta.", exception.getMessage());
    }

    @Test
    void atualizarConsultaReturnsOkWhenSuccessful() {
        int consultaId = 1;
        AgendamentoRequest request = new AgendamentoRequest();
        MensagemResponse response = new MensagemResponse("Consulta atualizada com sucesso!");
        when(consultaService.atualizarConsulta(request, consultaId)).thenReturn(response);

        ResponseEntity<MensagemResponse> result = agendamentoController.atualizarConsulta(consultaId, request);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    void atualizarConsultaReturnsNotFoundWhenConsultaDoesNotExist() {
        int consultaId = 1;
        AgendamentoRequest request = new AgendamentoRequest();
        when(consultaService.atualizarConsulta(request, consultaId))
                .thenThrow(new IllegalArgumentException("Consulta não encontrada."));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            agendamentoController.atualizarConsulta(consultaId, request);
        });

        assertEquals("Consulta não encontrada.", exception.getMessage());
    }

    @Test
    void atualizarConsultaReturnsInternalServerErrorOnUnexpectedError() {
        int consultaId = 1;
        AgendamentoRequest request = new AgendamentoRequest();
        when(consultaService.atualizarConsulta(request, consultaId))
                .thenThrow(new RuntimeException("Erro interno ao atualizar consulta."));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            agendamentoController.atualizarConsulta(consultaId, request);
        });

        assertEquals("Erro interno ao atualizar consulta.", exception.getMessage());
    }

    @Test
    void deletarConsultaReturnsOkWhenSuccessful() {
        int consultaId = 1;
        MensagemResponse response = new MensagemResponse("Consulta deletada com sucesso!");
        when(consultaService.deletar(consultaId)).thenReturn(response);

        ResponseEntity<MensagemResponse> result = agendamentoController.deletarConsulta(consultaId);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    void deletarConsultaReturnsNotFoundWhenConsultaDoesNotExist() {
        int consultaId = 1;
        when(consultaService.deletar(consultaId))
                .thenThrow(new IllegalArgumentException("Consulta não encontrada."));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            agendamentoController.deletarConsulta(consultaId);
        });

        assertEquals("Consulta não encontrada.", exception.getMessage());
    }

    @Test
    void deletarConsultaReturnsInternalServerErrorOnUnexpectedError() {
        int consultaId = 1;
        when(consultaService.deletar(consultaId))
                .thenThrow(new RuntimeException("Erro interno ao deletar consulta."));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            agendamentoController.deletarConsulta(consultaId);
        });

        assertEquals("Erro interno ao deletar consulta.", exception.getMessage());
    }
}

