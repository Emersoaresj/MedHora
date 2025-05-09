package br.com.fiap.medhora.application.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AgendamentoRequest {

    @NotNull(message = "O nome do paciente não pode ser nulo")
    private String nomePaciente;

    @NotNull(message = "O nome do médico não pode ser nulo")
    private String nomeMedico;

    @NotNull(message = "A data e hora da consulta não pode ser nula")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime dataHoraConsulta;

    @NotNull(message = "O motivo da consulta não pode ser nulo")
    private String motivoConsulta;
}
