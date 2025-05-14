package br.com.fiap.medhora.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NotificacaoConsultaDTO {
    private String nomePaciente;
    private String dataHoraConsulta;
    private String motivoConsulta;
}
