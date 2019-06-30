package br.com.dbc.wallet.Exception;

public class BuscaNullException extends Exception {
    private final String MENSAGEM = "Nenhum registro foi encontrado no banco de dados.";

    @Override
    public String getMessage() {
        return MENSAGEM;
    }
}
