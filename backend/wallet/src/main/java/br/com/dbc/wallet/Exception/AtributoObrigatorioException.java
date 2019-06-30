package br.com.dbc.wallet.Exception;

public class AtributoObrigatorioException extends Exception {
    private final String MENSAGEM = "Atributo obrigatório não informado.";

    @Override
    public String getMessage() {
        return MENSAGEM;
    }
}
