package br.com.fiap.medhora.kafka.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NotificacaoConsultaDTO {
    private String nomePaciente;
    private String dataHoraConsulta;
    private String motivoConsulta;
}
