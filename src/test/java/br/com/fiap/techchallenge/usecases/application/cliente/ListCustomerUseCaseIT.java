package br.com.fiap.techchallenge.usecases.application.cliente;

import br.com.fiap.techchallenge.application.usecases.costumers.ListCustomerUseCase;
import br.com.fiap.techchallenge.domain.ErrorsEnum;
import br.com.fiap.techchallenge.infra.exception.CustomerException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@ActiveProfiles("integration-test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ListCustomerUseCaseIT {

    @Autowired
    private ListCustomerUseCase listCustomerUseCase;

    @Test
    void deveRetornarListaVaziaQuandoNaoExistiremClientes() {
        CustomerException exception = assertThrows(CustomerException.class, () -> {
            listCustomerUseCase.listCustomers("nonexistent@example.com", "00000000000");
        });
        assertEquals(ErrorsEnum.CLIENTE_NAO_ENCONTRADO.getMessage(), exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoEmailForInvalido() {
        CustomerException exception = assertThrows(CustomerException.class, () -> {
            listCustomerUseCase.listCustomers("invalid-email", "12345678901");
        });

        assertEquals(ErrorsEnum.CLIENTE_NAO_ENCONTRADO.getMessage(), exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoCpfForInvalido() {
        CustomerException exception = assertThrows(CustomerException.class, () -> {
            listCustomerUseCase.listCustomers("joao.saladinha@example.com", "invalid-cpf");
        });

        assertEquals(ErrorsEnum.CLIENTE_NAO_ENCONTRADO.getMessage(), exception.getMessage());
    }
}
