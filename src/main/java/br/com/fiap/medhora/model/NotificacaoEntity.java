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
@Table(name = "notificacoes")
public class NotificacaoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_notificacao")
    private Integer idNotificacao;

    @Column(name = "nome_paciente")
    private String nomePaciente;

    @Column(name = "data_hora_consulta")
    private LocalDateTime dataHoraConsulta;

    @Column(name = "motivo_consulta")
    private String motivoConsulta;

    @Column(name = "data_envio_lembrete")
    private LocalDateTime dataEnvioLembrete;

    @Column(name = "status")
    private String status; // EX: ENVIADO, ERRO, PENDENTE

}

