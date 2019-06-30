package br.com.dbc.wallet.Exception;

public class UsuarioInvalidoException extends Exception {
    private final String MENSAGEM = "O usuário informado não é válido.";

    @Override
    public String getMessage() {
        return MENSAGEM;
    }
}
