package br.com.fiap.medhora.infrastructure.mapper;

import br.com.fiap.medhora.adapter.kafka.dto.NotificacaoConsultaDTO;
import br.com.fiap.medhora.model.NotificacaoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface NotificacaoMapper {

    NotificacaoMapper INSTANCE = Mappers.getMapper(NotificacaoMapper.class);

    @Mapping(target = "dataHoraConsulta", ignore = true)
    @Mapping(target = "idNotificacao", ignore = true)
    @Mapping(target = "dataEnvioLembrete", ignore = true)
    @Mapping(target = "status", ignore = true)
    NotificacaoEntity kafkaDtoToEntity(NotificacaoConsultaDTO entity);
}
