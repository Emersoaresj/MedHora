package br.com.fiap.medhora.graphql.service.port;

import br.com.fiap.medhora.graphql.dto.AgendamentoDTO;

import java.util.List;

public interface AgendamentoGraphQLServicePort {

    AgendamentoDTO buscarPorId(Integer idConsulta);
    List<AgendamentoDTO> buscarTodosOuPorPaciente(String nomePaciente, boolean somenteFuturos);

}
