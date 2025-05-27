package br.com.fiap.medhora.kafka.consumer;

import br.com.fiap.medhora.shared.exception.notificacao.ErroConsumerKafkaException;
import br.com.fiap.medhora.adapter.kafka.consumer.NotificacaoConsumer;
import br.com.fiap.medhora.adapter.kafka.dto.NotificacaoConsultaDTO;
import br.com.fiap.medhora.infrastructure.mapper.NotificacaoMapper;
import br.com.fiap.medhora.model.NotificacaoEntity;
import br.com.fiap.medhora.infrastructure.repository.NotificacaoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class NotificacaoConsumerTest {

    @Mock
    private NotificacaoRepository repository;

    @Mock
    private NotificacaoMapper notificacaoMapper;

    @InjectMocks
    private NotificacaoConsumer notificacaoConsumer;

    @Test
    void processarNotificacaoSavesEntityWhenValidMessageReceived() {
        NotificacaoConsultaDTO notificacaoDTO = new NotificacaoConsultaDTO();
        notificacaoDTO.setDataHoraConsulta("2023-10-10T10:00:00");
        notificacaoDTO.setNomePaciente("John Doe");

        NotificacaoEntity entity = new NotificacaoEntity();
        lenient().when(notificacaoMapper.kafkaDtoToEntity(notificacaoDTO)).thenReturn(entity);

        notificacaoConsumer.processarNotificacao(notificacaoDTO);

        ArgumentCaptor<NotificacaoEntity> captor = ArgumentCaptor.forClass(NotificacaoEntity.class);
        verify(repository).save(captor.capture());

        NotificacaoEntity savedEntity = captor.getValue();
        assertEquals("John Doe", savedEntity.getNomePaciente());
        assertEquals("2023-10-10T10:00", savedEntity.getDataHoraConsulta().toString());
        assertEquals("ENVIADO", savedEntity.getStatus());
        assertNotNull(savedEntity.getDataEnvioLembrete());
    }

    @Test
    void processarNotificacaoThrowsErroConsumerKafkaExceptionWhenErrorOccurs() {
        NotificacaoConsultaDTO notificacaoDTO = new NotificacaoConsultaDTO();
        notificacaoDTO.setDataHoraConsulta("invalid-date");

        Exception exception = assertThrows(ErroConsumerKafkaException.class, () -> {
            notificacaoConsumer.processarNotificacao(notificacaoDTO);
        });

        assertTrue(exception.getMessage().contains("Erro ao processar a notificação"));
    }
}