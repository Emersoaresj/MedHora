package br.com.fiap.medhora.graphql;

import br.com.fiap.medhora.adapter.dto.AgendamentoDTO;
import br.com.fiap.medhora.adapter.graphQL.resolver.AgendamentoResolver;
import br.com.fiap.medhora.infrastructure.graphql.port.AgendamentoGraphQLServicePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class AgendamentoResolverTest {

    @Mock
    private AgendamentoGraphQLServicePort service;

    @InjectMocks
    private AgendamentoResolver resolver;

    @Test
    void agendamentoPorIdReturnsDtoWhenIdExists() {
        AgendamentoDTO dto = new AgendamentoDTO();
        dto.setId(1);
        dto.setPaciente("John Doe");

        when(service.buscarPorId(1)).thenReturn(dto);

        AgendamentoDTO result = resolver.agendamentoPorId(1);

        assertEquals(1, result.getId());
        assertEquals("John Doe", result.getPaciente());
    }

    @Test
    void agendamentoPorIdThrowsExceptionWhenIdDoesNotExist() {
        when(service.buscarPorId(1)).thenThrow(new RuntimeException("Not found"));

        assertThrows(RuntimeException.class, () -> resolver.agendamentoPorId(1));
    }

    @Test
    void agendamentosReturnsListWhenValidArgumentsProvided() {
        List<AgendamentoDTO> dtos = List.of(
                new AgendamentoDTO(1, LocalDateTime.of(2023, 10, 10, 10, 0), "Consulta Rotina", "John Doe", "Dr. Smith"),
                new AgendamentoDTO(2, LocalDateTime.of(2023, 11, 11, 11, 0), "Consulta Checkup", "Jane Doe", "Dr. Brown")
        );

        when(service.buscarTodosOuPorPaciente("John Doe", true)).thenReturn(dtos);

        List<AgendamentoDTO> result = resolver.agendamentos("John Doe", true);

        assertEquals(2, result.size());
        assertEquals("John Doe", result.get(0).getPaciente());
        assertEquals("Consulta Rotina", result.get(0).getDescricao());
    }


    @Test
    void agendamentosReturnsEmptyListWhenNoMatchesFound() {
        when(service.buscarTodosOuPorPaciente("Nonexistent", true)).thenReturn(Collections.emptyList());

        List<AgendamentoDTO> result = resolver.agendamentos("Nonexistent", true);

        assertTrue(result.isEmpty());
    }
}