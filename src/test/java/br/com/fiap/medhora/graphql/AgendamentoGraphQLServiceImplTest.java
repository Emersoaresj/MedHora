package br.com.fiap.medhora.graphql;

import br.com.fiap.medhora.shared.exception.agendamento.AgendamentoNotExistsException;
import br.com.fiap.medhora.adapter.dto.AgendamentoDTO;
import br.com.fiap.medhora.infrastructure.graphql.impl.AgendamentoGraphQLServiceImpl;
import br.com.fiap.medhora.model.AgendamentoEntity;
import br.com.fiap.medhora.infrastructure.repository.AgendamentoRepository;
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
public class AgendamentoGraphQLServiceImplTest {

    @Mock
    private AgendamentoRepository agendamentoRepository;
    @InjectMocks
    AgendamentoGraphQLServiceImpl agendamentoGraphQLService;

    @Test
    void buscarPorIdReturnsDtoWhenEntityExists() {
        AgendamentoEntity entity = new AgendamentoEntity();
        entity.setIdConsulta(1);
        entity.setNomePaciente("John Doe");
        entity.setMotivoConsulta("Rotina");
        entity.setDataHoraConsulta(LocalDateTime.of(2023, 10, 10, 10, 0));
        entity.setNomeMedico("Dr. Smith");

        when(agendamentoRepository.findById(1)).thenReturn(java.util.Optional.of(entity));

        AgendamentoDTO result = agendamentoGraphQLService.buscarPorId(1);

        assertEquals(1, result.getId());
        assertEquals("John Doe", result.getPaciente());
        assertEquals("Rotina", result.getDescricao());
        assertEquals(LocalDateTime.of(2023, 10, 10, 10, 0), result.getDataHora());
        assertEquals("Dr. Smith", result.getMedico());
    }

    @Test
    void buscarPorIdThrowsExceptionWhenEntityDoesNotExist() {
        when(agendamentoRepository.findById(1)).thenReturn(java.util.Optional.empty());

        assertThrows(AgendamentoNotExistsException.class, () -> agendamentoGraphQLService.buscarPorId(1));
    }

    @Test
    void buscarTodosOuPorPacienteReturnsDtosWhenEntitiesExist() {
        List<AgendamentoEntity> entities = List.of(
                new AgendamentoEntity(1, "John Doe", "Dr. Smith", LocalDateTime.of(2023, 10, 10, 10, 0), "Rotina"),
                new AgendamentoEntity(2, "Jane Doe", "Dr. Brown", LocalDateTime.of(2023, 11, 11, 11, 0), "Checkup")
        );

        when(agendamentoRepository.findByNomePaciente("John Doe")).thenReturn(entities);

        List<AgendamentoDTO> result = agendamentoGraphQLService.buscarTodosOuPorPaciente("John Doe", false);

        assertEquals(2, result.size());
        assertEquals("John Doe", result.get(0).getPaciente());
        assertEquals("Checkup", result.get(1).getDescricao());
    }

    @Test
    void buscarTodosOuPorPacienteReturnsEmptyListWhenNoEntitiesExist() {
        when(agendamentoRepository.findByNomePaciente("Nonexistent")).thenReturn(Collections.emptyList());

        List<AgendamentoDTO> result = agendamentoGraphQLService.buscarTodosOuPorPaciente("Nonexistent", false);

        assertTrue(result.isEmpty());
    }

    @Test
    void buscarTodosOuPorPacienteFiltersByFutureDatesWhenSomenteFuturosIsTrue() {
        LocalDateTime now = LocalDateTime.now();
        List<AgendamentoEntity> entities = List.of(
                new AgendamentoEntity(1, "John Doe", "Rotina", now.plusDays(1), "Dr. Smith")
        );

        when(agendamentoRepository.findByNomePacienteAndDataHoraConsultaAfter("John Doe", now))
                .thenReturn(entities);

        List<AgendamentoDTO> result = agendamentoGraphQLService.buscarTodosOuPorPaciente("John Doe", true);

        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getPaciente());
        assertTrue(result.get(0).getDataHora().isAfter(now));
    }
}
