package br.com.fiap.medhora.application.service.port;

import br.com.fiap.medhora.application.request.AgendamentoRequest;
import br.com.fiap.medhora.application.response.ConsultasResponse;
import br.com.fiap.medhora.application.MensagemResponse;

import java.util.List;

public interface AgendamentoServicePort {

    MensagemResponse cadastrarConsulta(AgendamentoRequest consulta);
    List<ConsultasResponse> listarTodasConsultas();
    ConsultasResponse buscarConsulta(Integer id);
    MensagemResponse deletar(Integer id);
    MensagemResponse atualizarConsulta(AgendamentoRequest consulta, Integer id);

}
