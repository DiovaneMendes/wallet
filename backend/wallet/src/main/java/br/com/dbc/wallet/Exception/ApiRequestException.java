package br.com.dbc.wallet.Exception;

public class ApiRequestException extends Exception {
    private final String MENSAGEM = "Falha ao realizar busca na api.";

    @Override
    public String getMessage() {
        return MENSAGEM;
    }
}
