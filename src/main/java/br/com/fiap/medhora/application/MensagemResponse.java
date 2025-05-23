package br.com.fiap.medhora.application;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Resposta padrão de mensagens do sistema.")
public class MensagemResponse {

    @Schema(description = "Mensagem de resposta", example = "Consulta cadastrada com sucesso!")
    private String mensagem;
}