package br.com.dbc.wallet.Exception;

public class ServicoInvalidoException extends Exception {
    private final String MENSAGEM = "O serviço informado não é válido.";
    private String mensagemExtra = " ";

    public ServicoInvalidoException() {}

    public ServicoInvalidoException(String msgExtra) {
        this.mensagemExtra = mensagemExtra.concat(msgExtra);
    }

    @Override
    public String getMessage() {
        return MENSAGEM.concat(mensagemExtra);
    }
}
