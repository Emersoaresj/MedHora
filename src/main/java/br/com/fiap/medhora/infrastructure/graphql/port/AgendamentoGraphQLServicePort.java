package br.com.fiap.medhora.infrastructure.graphql.port;

import br.com.fiap.medhora.adapter.dto.AgendamentoDTO;

import java.util.List;

public interface AgendamentoGraphQLServicePort {

    AgendamentoDTO buscarPorId(Integer idConsulta);
    List<AgendamentoDTO> buscarTodosOuPorPaciente(String nomePaciente, boolean somenteFuturos);

}
