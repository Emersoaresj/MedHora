package br.com.fiap.medhora.controller;

import br.com.fiap.medhora.application.request.AgendamentoRequest;
import br.com.fiap.medhora.application.response.ConsultasResponse;
import br.com.fiap.medhora.application.MensagemResponse;
import br.com.fiap.medhora.service.port.AgendamentoServicePort;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consultas")
public class AgendamentoController {


    @Autowired
    private AgendamentoServicePort consultaService;


    @PostMapping
    public ResponseEntity<MensagemResponse> cadastrarConsulta(@Valid @RequestBody AgendamentoRequest consulta) {
        return ResponseEntity.status(HttpStatus.OK).body(consultaService.cadastrarConsulta(consulta));
    }

    @GetMapping
    public ResponseEntity<List<ConsultasResponse>> listarTodasConsultas() {
        return ResponseEntity.status(HttpStatus.OK).body(consultaService.listarTodasConsultas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultasResponse> buscarConsulta(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(consultaService.buscarConsulta(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MensagemResponse> atualizarConsulta(@Valid @RequestBody AgendamentoRequest consulta,
                                                               @PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(consultaService.atualizarConsulta(consulta, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MensagemResponse> deletar(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(consultaService.deletar(id));
    }
}
