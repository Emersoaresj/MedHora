package br.com.fiap.medhora.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "agendamento_consulta")
public class AgendamentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_consulta")
    private Integer idConsulta;

    @Column(name = "nome_paciente")
    private String nomePaciente;

    @Column(name = "nome_medico")
    private String nomeMedico;

    @Column(name = "data_hora_consulta")
    private LocalDateTime dataHoraConsulta;

    @Column(name = "motivo_consulta")
    private String motivoConsulta;
}