package br.com.fiap.medhora.controller;

import br.com.fiap.medhora.application.request.AgendamentoRequest;
import br.com.fiap.medhora.application.response.ConsultasResponse;
import br.com.fiap.medhora.application.MensagemResponse;
import br.com.fiap.medhora.service.port.AgendamentoServicePort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consultas")
@Tag(name = "Consultas Médicas", description = "Endpoints para gerenciamento de consultas médicas")
public class AgendamentoController {

    @Autowired
    private AgendamentoServicePort consultaService;

    @PostMapping
    @Operation(
            summary = "Cadastrar uma nova consulta",
            description = "Recebe os dados de uma nova consulta médica e cadastra no sistema.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Consulta cadastrada com sucesso.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MensagemResponse.class),
                                    examples = @ExampleObject(value = "{\"mensagem\": \"Consulta cadastrada com sucesso!\"}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Consulta já existe para este paciente.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MensagemResponse.class),
                                    examples = @ExampleObject(value = "{\"mensagem\": \"Consulta já existe para este paciente.\"}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Erro interno ao cadastrar consulta.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MensagemResponse.class),
                                    examples = @ExampleObject(value = "{\"mensagem\": \"Erro interno ao cadastrar consulta.\"}")
                            )
                    )
            }
    )
    public ResponseEntity<MensagemResponse> cadastrarConsulta(@Valid @RequestBody AgendamentoRequest consulta) {
        return ResponseEntity.status(HttpStatus.CREATED).body(consultaService.cadastrarConsulta(consulta));
    }

    @GetMapping
    @Operation(
            summary = "Listar todas as consultas",
            description = "Retorna uma lista com todas as consultas cadastradas no sistema.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Consultas listadas com sucesso.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ConsultasResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Erro interno ao listar consultas.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MensagemResponse.class),
                                    examples = @ExampleObject(value = "{\"mensagem\": \"Erro interno ao listar consultas.\"}")
                            )
                    )
            }
    )
    public ResponseEntity<List<ConsultasResponse>> listarTodasConsultas() {
        return ResponseEntity.status(HttpStatus.OK).body(consultaService.listarTodasConsultas());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar consulta por ID",
            description = "Busca e retorna uma consulta médica específica, informando o ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Consulta encontrada com sucesso.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ConsultasResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Consulta não encontrada.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MensagemResponse.class),
                                    examples = @ExampleObject(value = "{\"mensagem\": \"Consulta não encontrada.\"}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Erro interno ao buscar consulta.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MensagemResponse.class),
                                    examples = @ExampleObject(value = "{\"mensagem\": \"Erro interno ao buscar consulta.\"}")
                            )
                    )
            }
    )
    public ResponseEntity<ConsultasResponse> buscarConsulta(
            @Parameter(description = "ID da consulta", example = "1") @PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(consultaService.buscarConsulta(id));
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar uma consulta",
            description = "Atualiza os dados de uma consulta existente.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Consulta atualizada com sucesso",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MensagemResponse.class),
                                    examples = @ExampleObject(value = "{\"mensagem\": \"Consulta atualizada com sucesso!\"}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Consulta não encontrada",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MensagemResponse.class),
                                    examples = @ExampleObject(value = "{\"mensagem\": \"Consulta não encontrada.\"}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Erro interno ao atualizar consulta",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MensagemResponse.class),
                                    examples = @ExampleObject(value = "{\"mensagem\": \"Erro interno ao atualizar consulta.\"}")
                            )
                    )
            }
    )
    public ResponseEntity<MensagemResponse> atualizarConsulta(
            @PathVariable Integer id, @Valid @RequestBody AgendamentoRequest agendamentoRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(consultaService.atualizarConsulta(agendamentoRequest, id));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Deletar uma consulta",
            description = "Remove uma consulta médica cadastrada, informando o ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Consulta deletada com sucesso.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MensagemResponse.class),
                                    examples = @ExampleObject(value = "{\"mensagem\": \"Consulta deletada com sucesso!\"}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Consulta não encontrada.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MensagemResponse.class),
                                    examples = @ExampleObject(value = "{\"mensagem\": \"Consulta não encontrada.\"}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Erro interno ao deletar consulta.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MensagemResponse.class),
                                    examples = @ExampleObject(value = "{\"mensagem\": \"Erro interno ao deletar consulta.\"}")
                            )
                    )
            }
    )
    public ResponseEntity<MensagemResponse> deletarConsulta(
            @Parameter(description = "ID da consulta", example = "1") @PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(consultaService.deletar(id));
    }
}
