package br.com.fiap.techchallenge.infra.exception;

import br.com.fiap.techchallenge.domain.ErrorResponse;
import br.com.fiap.techchallenge.domain.ErrorsEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;


@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    String format = "Handling exception: {}";

    private ResponseEntity<ErrorResponse> buildResponseEntity(String errorCode, String message, ErrorsEnum errorEnum) {
        log.info(format, errorEnum);
        ErrorResponse errorResponse = new ErrorResponse(errorCode, message);
        return new ResponseEntity<>(errorResponse, errorEnum.getHttpStatusCode());
    }

    @ExceptionHandler(CustomerAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleCustomerAlreadyExistsException(CustomerAlreadyExistsException ex, WebRequest request) {
        log.info(format, ErrorsEnum.CLIENTE_JA_CADASTRADO);
        return buildResponseEntity(ex.getErrorCode(), ex.getMessage(), ErrorsEnum.CLIENTE_JA_CADASTRADO);
    }

    @ExceptionHandler(CustomerException.class)
    public ResponseEntity<ErrorResponse> handleCustomerException(CustomerException ex, WebRequest request) {
        log.info(format, ex);
        ErrorsEnum errorEnum = ErrorsEnum.valueOf(ex.getError().toString());
        return buildResponseEntity(String.valueOf(ex.getHttpStatusCode().value()), ex.getMessage(), errorEnum);
    }
}
