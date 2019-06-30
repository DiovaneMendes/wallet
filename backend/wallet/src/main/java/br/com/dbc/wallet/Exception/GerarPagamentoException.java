package br.com.dbc.wallet.Exception;

public class GerarPagamentoException extends Exception {
    private final String MENSAGEM = "Falha ao gerar o pagamento do serviço.";

    @Override
    public String getMessage() {
        return MENSAGEM;
    }
}
