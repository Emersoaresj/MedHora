package br.com.fiap.medhora.application.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "Resposta com os dados da consulta agendada.")
public class ConsultasResponse {

    @Schema(description = "Identificador único da consulta", example = "1")
    private Integer idConsulta;

    @Schema(description = "Nome completo do médico", example = "Dra. Maria Souza")
    private String nomeMedico;

    @Schema(description = "Nome completo do paciente", example = "João Silva")
    private String nomePaciente;

    @Schema(description = "Data e hora da consulta", example = "2025-05-22T14:30")
    private LocalDateTime dataHoraConsulta;

    @Schema(description = "Motivo da consulta", example = "Consulta de rotina")
    private String motivoConsulta;
}
