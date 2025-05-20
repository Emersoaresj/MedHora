package br.com.fiap.medhora.exception.graphql;

import br.com.fiap.medhora.exception.agendamento.AgendamentoNotExistsException;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class GlobalExceptionGraphQL extends DataFetcherExceptionResolverAdapter {

    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
        if (ex instanceof AgendamentoNotExistsException) {
            return GraphqlErrorBuilder.newError()
                    .message(ex.getMessage())
                    .path(env.getExecutionStepInfo().getPath())
                    .errorType(ErrorType.BAD_REQUEST)
                    .extensions(Map.of("classification", "BAD_REQUEST"))
                    .build();
        }
        return null;
    }
}


