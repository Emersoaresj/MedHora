package br.com.fiap.medhora.controller;

import br.com.fiap.medhora.application.MensagemResponse;
import br.com.fiap.medhora.application.request.NotificacaoRequest;
import br.com.fiap.medhora.application.response.NotificacaoResponse;
import br.com.fiap.medhora.service.port.NotificacaoServicePort;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notificacao")
public class NotificacaoController {

    @Autowired
    private NotificacaoServicePort servicePort;


    @PostMapping
    public ResponseEntity<MensagemResponse> criarNotificacao(@Valid @RequestBody NotificacaoRequest request){
        return ResponseEntity.status(HttpStatus.OK).body(servicePort.criarNotificacao(request));
    }

    @GetMapping("/{idNotificacao}")
    public ResponseEntity<NotificacaoResponse> buscarNotificacaoPorId(@PathVariable Integer idNotificacao){
        return ResponseEntity.status(HttpStatus.OK).body(servicePort.buscarNotificacaoPorId(idNotificacao));
    }

    @GetMapping("/listar")
    public ResponseEntity<List<NotificacaoResponse>> listarNotificacoes(){
        return ResponseEntity.status(HttpStatus.OK).body(servicePort.listarNotificacoes());
    }

}
