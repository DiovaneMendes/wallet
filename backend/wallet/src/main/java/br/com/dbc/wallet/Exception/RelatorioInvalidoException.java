package br.com.dbc.wallet.Exception;

public class RelatorioInvalidoException extends Exception {
    private final String MENSAGEM = "O usuario não possue serviços contratados.";

    @Override
    public String getMessage() {
        return MENSAGEM;
    }
}
