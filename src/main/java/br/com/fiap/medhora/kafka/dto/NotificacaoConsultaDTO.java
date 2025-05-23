package br.com.fiap.medhora.kafka.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "DTO para notificação de consulta via Kafka.")
public class NotificacaoConsultaDTO {

    @Schema(description = "Nome do paciente", example = "João Silva")
    private String nomePaciente;

    @Schema(description = "Data e hora da consulta formatada", example = "2025-05-22T14:30")
    private String dataHoraConsulta;

    @Schema(description = "Motivo da consulta", example = "Consulta de rotina")
    private String motivoConsulta;
}
