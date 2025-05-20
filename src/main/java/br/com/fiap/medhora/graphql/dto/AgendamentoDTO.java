package br.com.fiap.medhora.graphql.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgendamentoDTO {
    private Integer id;
    private LocalDateTime dataHora;
    private String descricao;
    private String paciente;
    private String medico;
}
