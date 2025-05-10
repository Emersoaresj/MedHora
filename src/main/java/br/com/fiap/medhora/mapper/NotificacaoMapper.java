package br.com.fiap.medhora.mapper;

import br.com.fiap.medhora.application.request.NotificacaoRequest;
import br.com.fiap.medhora.application.response.NotificacaoResponse;
import br.com.fiap.medhora.model.NotificacaoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface NotificacaoMapper {

    NotificacaoMapper INSTANCE = Mappers.getMapper(NotificacaoMapper.class);

    @Mapping(target = "idNotificacao", ignore = true)
    NotificacaoEntity requestToEntity (NotificacaoRequest request);

    NotificacaoResponse entityToResponse (NotificacaoEntity entity);

    List<NotificacaoResponse> entitysToResponses (List<NotificacaoEntity> entity);
}
