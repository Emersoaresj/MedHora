package br.com.fiap.medhora.mapper;

import br.com.fiap.medhora.kafka.dto.NotificacaoConsultaDTO;
import br.com.fiap.medhora.model.NotificacaoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface NotificacaoMapper {

    NotificacaoMapper INSTANCE = Mappers.getMapper(NotificacaoMapper.class);

    @Mapping(target = "dataHoraConsulta", ignore = true)
    NotificacaoEntity kafkaDtoToEntity(NotificacaoConsultaDTO entity);
}
