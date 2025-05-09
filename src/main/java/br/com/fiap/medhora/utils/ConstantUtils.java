package br.com.fiap.medhora.utils;

import lombok.Data;

@Data
public class ConstantUtils {




    private ConstantUtils() {
        throw new IllegalStateException("Classe Utilitária");
    }

    //ERROS
    public static final String CONSULTA_EXISTENTE = "Consulta já existe para este paciente!";
    public static final String AGENDAMENTO_NAO_ENCONTRADO = "Consulta não encontrada!";

    //SUCESSOS
    public static final String CONSULTA_CADASTRADA = "Consulta cadastrada com sucesso!";
    public static final String CONSULTA_ATUALIZADA = "Consulta atualizada com sucesso!";
    public static final String CONSULTA_DELETADA = "Consulta deletada com sucesso!";
}
