package br.com.fiap.medhora.application.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificacaoRequest {

    @NotBlank(message = "Nome do paciente é obrigatório")
    private String nomePaciente;

    @NotNull(message = "A data e hora da consulta não pode ser nula")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime dataHoraConsulta;

    @NotBlank(message = "Motivo da consulta é obrigatório")
    private String motivoConsulta;
}
