package br.com.fiap.techchallenge.infra.exception;

import br.com.fiap.techchallenge.domain.ErrorsEnum;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class BaseException extends RuntimeException {
    private String message;
    private ErrorsEnum error;
    private HttpStatus httpStatusCode;

    public BaseException(ErrorsEnum erro, String... violations) {
        super(String.format(erro.getMessage(), (Object) violations));
        this.error = erro;
        this.message = erro.getMessage();
        this.httpStatusCode = erro.getHttpStatusCode();
    }

    public BaseException(String erro, String[] violations) {
        super(String.format(erro, (Object) violations));
        this.error = ErrorsEnum.ERRO_PARAMETROS;
        this.message = erro;
        this.httpStatusCode = HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
