package br.com.fiap.medhora.kafka.producer;

import br.com.fiap.medhora.adapter.kafka.dto.NotificacaoConsultaDTO;
import br.com.fiap.medhora.adapter.kafka.producer.impl.ConsultaProducerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ConsultaProducerServiceImplTest {

    @Mock
    private KafkaTemplate<String, Object> kafkaTemplate;

    private ConsultaProducerServiceImpl consultaProducerService;

    private static final String TOPICO = "notificacoes-consulta";

    @BeforeEach
    void setUp() {
        consultaProducerService = new ConsultaProducerServiceImpl(kafkaTemplate);
    }

    @Test
    void enviarMensagemPublishesMessageToKafkaTopic() {
        NotificacaoConsultaDTO dto = new NotificacaoConsultaDTO();
        dto.setDataHoraConsulta("2023-10-10T10:00:00");
        dto.setNomePaciente("John Doe");
        dto.setMotivoConsulta("Rotina");

        consultaProducerService.enviarMensagem(dto);

        verify(kafkaTemplate).send(TOPICO, dto);
    }

    @Test
    void enviarMensagemHandlesNullMessageGracefully() {
        NotificacaoConsultaDTO dto = null;

        assertDoesNotThrow(() -> consultaProducerService.enviarMensagem(dto));
        verify(kafkaTemplate).send("notificacoes-consulta", dto);
    }

}