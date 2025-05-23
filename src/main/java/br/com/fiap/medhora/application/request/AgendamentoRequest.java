package br.com.fiap.medhora.application.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "Requisição para agendar uma nova consulta.")
public class AgendamentoRequest {

    @NotNull(message = "O nome do paciente não pode ser nulo")
    @Schema(description = "Nome completo do paciente", example = "João Silva")
    private String nomePaciente;

    @NotNull(message = "O nome do médico não pode ser nulo")
    @Schema(description = "Nome completo do médico", example = "Dra. Maria Souza")
    private String nomeMedico;

    @NotNull(message = "A data e hora da consulta não pode ser nula")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Schema(description = "Data e hora da consulta no formato ISO", example = "2025-05-22T14:30")
    private LocalDateTime dataHoraConsulta;

    @NotNull(message = "O motivo da consulta não pode ser nulo")
    @Schema(description = "Motivo da consulta", example = "Consulta de rotina")
    private String motivoConsulta;
}
