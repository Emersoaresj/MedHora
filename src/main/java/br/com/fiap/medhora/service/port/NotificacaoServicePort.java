package br.com.fiap.medhora.service.port;

import br.com.fiap.medhora.application.MensagemResponse;
import br.com.fiap.medhora.application.request.NotificacaoRequest;
import br.com.fiap.medhora.application.response.NotificacaoResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface NotificacaoServicePort {

    MensagemResponse criarNotificacao(NotificacaoRequest request);
    NotificacaoResponse buscarNotificacaoPorId(Integer idNotificacao);
    List<NotificacaoResponse> listarNotificacoes();

}
