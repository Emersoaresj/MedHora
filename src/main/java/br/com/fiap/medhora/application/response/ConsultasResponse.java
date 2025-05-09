package br.com.fiap.medhora.application.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ConsultasResponse {

    private Integer idConsulta;
    private String nomeMedico;
    private String nomePaciente;
    private LocalDateTime dataHoraConsulta;
    private String motivoConsulta;
}
