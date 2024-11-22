package br.com.fiap.techchallenge.infra.exception;

import br.com.fiap.techchallenge.domain.ErrorsEnum;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CustomerException extends BaseException {

    public CustomerException(ErrorsEnum erro, String... violations) {
        super(erro, violations);
    }

    public CustomerException(String erro, String... violations) {
        super(erro, violations);
    }

    public String getErrorCode() {
        return super.getErrorCode();
    }
}
