package br.com.fiap.techchallenge.infra.exception;

import br.com.fiap.techchallenge.domain.ErrorsEnum;
import lombok.Getter;

@Getter
public class CustomerAlreadyExistsException extends BaseException {
    public CustomerAlreadyExistsException(ErrorsEnum error) {
        super(error);
    }
}
