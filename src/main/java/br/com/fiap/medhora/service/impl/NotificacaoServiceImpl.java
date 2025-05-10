package br.com.fiap.medhora.service.impl;

import br.com.fiap.medhora.application.MensagemResponse;
import br.com.fiap.medhora.application.request.NotificacaoRequest;
import br.com.fiap.medhora.application.response.NotificacaoResponse;
import br.com.fiap.medhora.exception.ErroInternoException;
import br.com.fiap.medhora.exception.notificacao.NotificacaoExistsException;
import br.com.fiap.medhora.exception.notificacao.NotificacaoNotExistsException;
import br.com.fiap.medhora.mapper.AgendamentoMapper;
import br.com.fiap.medhora.mapper.NotificacaoMapper;
import br.com.fiap.medhora.model.AgendamentoEntity;
import br.com.fiap.medhora.model.NotificacaoEntity;
import br.com.fiap.medhora.repository.NotificacaoRepository;
import br.com.fiap.medhora.service.port.NotificacaoServicePort;
import br.com.fiap.medhora.utils.ConstantUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class NotificacaoServiceImpl implements NotificacaoServicePort {

    @Autowired
    private NotificacaoRepository repository;


    @Override
    public MensagemResponse criarNotificacao(NotificacaoRequest request) {

        if (repository.existsByNomePacienteAndDataHoraConsulta(request.getNomePaciente(), request.getDataHoraConsulta())) {
            throw new NotificacaoExistsException(ConstantUtils.NOTIFICACAO_EXISTENTE);
        }

        try {
            NotificacaoEntity entity = NotificacaoMapper.INSTANCE.requestToEntity(request);
            entity.setStatus("ENVIADO");
            entity.setDataEnvioLembrete(LocalDateTime.now());

            repository.save(entity);

            return new MensagemResponse(ConstantUtils.NOTIFICACAO_CADASTRADA);
        } catch (Exception e) {
            log.error("Erro ao cadastrar notificacao", e);
            throw new ErroInternoException("Erro ao cadastrar notificacao: " + e.getMessage());
        }

    }

    @Override
    public NotificacaoResponse buscarNotificacaoPorId(Integer idNotificacao) {

        NotificacaoEntity entity = repository.findById(idNotificacao).orElseThrow(() ->
                new NotificacaoNotExistsException(ConstantUtils.NOTIFICACAO_NAO_ENCONTRADA));

        return NotificacaoMapper.INSTANCE.entityToResponse(entity);
    }

    @Override
    public List<NotificacaoResponse> listarNotificacoes() {
        List<NotificacaoEntity> entity = repository.findAll();
        return NotificacaoMapper.INSTANCE.entitysToResponses(entity);
    }
}
