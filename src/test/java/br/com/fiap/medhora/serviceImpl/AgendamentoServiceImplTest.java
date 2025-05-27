package br.com.fiap.medhora.serviceImpl;

import br.com.fiap.medhora.application.MensagemResponse;
import br.com.fiap.medhora.application.request.AgendamentoRequest;
import br.com.fiap.medhora.application.response.ConsultasResponse;
import br.com.fiap.medhora.shared.exception.ErroInternoException;
import br.com.fiap.medhora.shared.exception.agendamento.AgendamentoExistsException;
import br.com.fiap.medhora.shared.exception.agendamento.AgendamentoNotExistsException;
import br.com.fiap.medhora.adapter.kafka.dto.NotificacaoConsultaDTO;
import br.com.fiap.medhora.adapter.kafka.producer.port.ConsultaProducerServicePort;
import br.com.fiap.medhora.model.AgendamentoEntity;
import br.com.fiap.medhora.infrastructure.repository.AgendamentoRepository;
import br.com.fiap.medhora.application.service.impl.AgendamentoServiceImpl;
import br.com.fiap.medhora.shared.utils.ConstantUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class AgendamentoServiceImplTest {

    @Mock
    private AgendamentoRepository repository;

    @Mock
    private ConsultaProducerServicePort consultaProducerService;

    @InjectMocks
    private AgendamentoServiceImpl agendamentoService;

    @Test
    void cadastrarConsultaSavesEntityAndPublishesMessageWhenValidRequest() {
        AgendamentoRequest request = new AgendamentoRequest();
        request.setNomePaciente("John Doe");
        request.setMotivoConsulta("Rotina");
        request.setDataHoraConsulta(LocalDateTime.of(2023, 10, 10, 10, 0));

        when(repository.existsByNomePacienteAndMotivoConsulta(request.getNomePaciente(), request.getMotivoConsulta()))
                .thenReturn(false);

        MensagemResponse response = agendamentoService.cadastrarConsulta(request);

        verify(repository).save(any(AgendamentoEntity.class));
        verify(consultaProducerService).enviarMensagem(any(NotificacaoConsultaDTO.class));
        assertEquals(ConstantUtils.CONSULTA_CADASTRADA, response.getMensagem());
    }

    @Test
    void cadastrarConsultaThrowsExceptionWhenDuplicateRequest() {
        AgendamentoRequest request = new AgendamentoRequest();
        request.setNomePaciente("John Doe");
        request.setMotivoConsulta("Rotina");

        when(repository.existsByNomePacienteAndMotivoConsulta(request.getNomePaciente(), request.getMotivoConsulta()))
                .thenReturn(true);

        assertThrows(AgendamentoExistsException.class, () -> agendamentoService.cadastrarConsulta(request));
        verifyNoInteractions(consultaProducerService);
    }

    @Test
    void cadastrarConsultaThrowsErroInternoExceptionWhenRepositoryFails() {
        AgendamentoRequest request = new AgendamentoRequest();
        request.setNomePaciente("John Doe");
        request.setMotivoConsulta("Rotina");
        request.setDataHoraConsulta(LocalDateTime.of(2023, 10, 10, 10, 0));

        when(repository.existsByNomePacienteAndMotivoConsulta(request.getNomePaciente(), request.getMotivoConsulta()))
                .thenReturn(false);
        doThrow(new RuntimeException("Database error")).when(repository).save(any(AgendamentoEntity.class));

        assertThrows(ErroInternoException.class, () -> agendamentoService.cadastrarConsulta(request));
        verifyNoInteractions(consultaProducerService);
    }

    @Test
    void listarTodasConsultasReturnsMappedResponsesWhenEntitiesExist() {
        AgendamentoEntity entity1 = new AgendamentoEntity();
        entity1.setIdConsulta(1);
        entity1.setNomePaciente("John Doe");
        entity1.setMotivoConsulta("Rotina");
        entity1.setDataHoraConsulta(LocalDateTime.of(2023, 10, 10, 10, 0));

        AgendamentoEntity entity2 = new AgendamentoEntity();
        entity2.setIdConsulta(2);
        entity2.setNomePaciente("Jane Doe");
        entity2.setMotivoConsulta("Checkup");
        entity2.setDataHoraConsulta(LocalDateTime.of(2023, 11, 11, 11, 0));

        List<AgendamentoEntity> entities = Arrays.asList(entity1, entity2);

        when(repository.findAll()).thenReturn(entities);

        List<ConsultasResponse> responses = agendamentoService.listarTodasConsultas();

        assertEquals(2, responses.size());
        assertEquals("John Doe", responses.get(0).getNomePaciente());
        assertEquals("Checkup", responses.get(1).getMotivoConsulta());
    }


    @Test
    void listarTodasConsultasReturnsEmptyListWhenNoEntitiesExist() {
        when(repository.findAll()).thenReturn(Collections.emptyList());

        List<ConsultasResponse> responses = agendamentoService.listarTodasConsultas();

        assertTrue(responses.isEmpty());
    }

    @Test
    void buscarConsultaReturnsResponseWhenEntityExists() {
        AgendamentoEntity entity = new AgendamentoEntity();
        entity.setIdConsulta(1);
        entity.setNomePaciente("John Doe");
        entity.setMotivoConsulta("Rotina");
        entity.setDataHoraConsulta(LocalDateTime.of(2023, 10, 10, 10, 0));

        when(repository.findById(1)).thenReturn(java.util.Optional.of(entity));

        ConsultasResponse response = agendamentoService.buscarConsulta(1);

        assertEquals("John Doe", response.getNomePaciente());
        assertEquals("Rotina", response.getMotivoConsulta());
        assertEquals(LocalDateTime.of(2023, 10, 10, 10, 0), response.getDataHoraConsulta());
    }

    @Test
    void buscarConsultaThrowsExceptionWhenEntityDoesNotExist() {
        when(repository.findById(1)).thenReturn(java.util.Optional.empty());

        assertThrows(AgendamentoNotExistsException.class, () -> agendamentoService.buscarConsulta(1));
    }

    @Test
    void atualizarConsultaUpdatesEntityAndPublishesMessageWhenValidRequest() {
        AgendamentoRequest request = new AgendamentoRequest();
        request.setNomePaciente("John Doe");
        request.setMotivoConsulta("Rotina");
        request.setDataHoraConsulta(LocalDateTime.of(2023, 10, 10, 10, 0));

        AgendamentoEntity existingEntity = new AgendamentoEntity();
        existingEntity.setIdConsulta(1);

        when(repository.findById(1)).thenReturn(java.util.Optional.of(existingEntity));

        MensagemResponse response = agendamentoService.atualizarConsulta(request, 1);

        verify(repository).save(any(AgendamentoEntity.class));
        verify(consultaProducerService).enviarMensagem(any(NotificacaoConsultaDTO.class));
        assertEquals(ConstantUtils.CONSULTA_ATUALIZADA, response.getMensagem());
    }

    @Test
    void atualizarConsultaThrowsExceptionWhenEntityDoesNotExist() {
        AgendamentoRequest request = new AgendamentoRequest();
        request.setNomePaciente("John Doe");
        request.setMotivoConsulta("Rotina");
        request.setDataHoraConsulta(LocalDateTime.of(2023, 10, 10, 10, 0));

        when(repository.findById(1)).thenReturn(java.util.Optional.empty());

        assertThrows(AgendamentoNotExistsException.class, () -> agendamentoService.atualizarConsulta(request, 1));
        verifyNoInteractions(consultaProducerService);
    }

    @Test
    void atualizarConsultaThrowsErroInternoExceptionWhenRepositoryFails() {
        AgendamentoRequest request = new AgendamentoRequest();
        request.setNomePaciente("John Doe");
        request.setMotivoConsulta("Rotina");
        request.setDataHoraConsulta(LocalDateTime.of(2023, 10, 10, 10, 0));

        AgendamentoEntity existingEntity = new AgendamentoEntity();
        existingEntity.setIdConsulta(1);

        when(repository.findById(1)).thenReturn(java.util.Optional.of(existingEntity));
        doThrow(new RuntimeException("Database error")).when(repository).save(any(AgendamentoEntity.class));

        assertThrows(ErroInternoException.class, () -> agendamentoService.atualizarConsulta(request, 1));
        verifyNoInteractions(consultaProducerService);
    }

    @Test
    void deletarRemovesEntityWhenIdExists() {
        AgendamentoEntity entity = new AgendamentoEntity();
        entity.setIdConsulta(1);

        when(repository.findById(1)).thenReturn(java.util.Optional.of(entity));

        MensagemResponse response = agendamentoService.deletar(1);

        verify(repository).deleteById(1);
        assertEquals(ConstantUtils.CONSULTA_DELETADA, response.getMensagem());
    }

    @Test
    void deletarThrowsExceptionWhenIdDoesNotExist() {
        when(repository.findById(1)).thenReturn(java.util.Optional.empty());

        assertThrows(AgendamentoNotExistsException.class, () -> agendamentoService.deletar(1));
        verify(repository, never()).deleteById(any());
    }

    @Test
    void deletarThrowsErroInternoExceptionWhenRepositoryFails() {
        AgendamentoEntity entity = new AgendamentoEntity();
        entity.setIdConsulta(1);

        when(repository.findById(1)).thenReturn(java.util.Optional.of(entity));
        doThrow(new RuntimeException("Database error")).when(repository).deleteById(1);

        assertThrows(ErroInternoException.class, () -> agendamentoService.deletar(1));
    }

}
