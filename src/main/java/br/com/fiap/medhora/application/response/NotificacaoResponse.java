package br.com.fiap.medhora.application.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificacaoResponse {

    private Integer idNotificacao;
    private String nomePaciente;
    private LocalDateTime dataHoraConsulta;
    private String motivoConsulta;
    private LocalDateTime dataEnvioLembrete;
    private String status;
}
