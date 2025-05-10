package br.com.fiap.medhora.mapper;
import br.com.fiap.medhora.application.request.AgendamentoRequest;
import br.com.fiap.medhora.application.response.ConsultasResponse;
import br.com.fiap.medhora.model.AgendamentoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper
public interface AgendamentoMapper {

    AgendamentoMapper INSTANCE = Mappers.getMapper(AgendamentoMapper.class);

    @Mapping(target = "idConsulta", ignore = true)
    AgendamentoEntity requestToEntity(AgendamentoRequest request);

    List<ConsultasResponse> entitysToResponses(List<AgendamentoEntity> entity);

    ConsultasResponse entityToResponse (AgendamentoEntity entity);
}
