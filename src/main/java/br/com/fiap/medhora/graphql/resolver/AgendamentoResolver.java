package br.com.fiap.medhora.graphql.resolver;

import br.com.fiap.medhora.graphql.dto.AgendamentoDTO;
import br.com.fiap.medhora.graphql.service.port.AgendamentoGraphQLServicePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class AgendamentoResolver {

    @Autowired
    private AgendamentoGraphQLServicePort service;

    @QueryMapping
    public AgendamentoDTO agendamentoPorId(@Argument Integer id) {
        return service.buscarPorId(id);
    }

    @QueryMapping
    public List<AgendamentoDTO> agendamentos(
            @Argument String nomePaciente,
            @Argument Boolean somenteFuturos
    ) {
        return service.buscarTodosOuPorPaciente(nomePaciente, Boolean.TRUE.equals(somenteFuturos));
    }
}

