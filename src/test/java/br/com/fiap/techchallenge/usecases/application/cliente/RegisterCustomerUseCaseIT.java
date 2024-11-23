package br.com.fiap.techchallenge.usecases.application.cliente;

import br.com.fiap.techchallenge.application.usecases.costumers.RegisterCustomerUseCase;
import br.com.fiap.techchallenge.domain.ErrorsEnum;
import br.com.fiap.techchallenge.domain.entities.customer.Customer;
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
class RegisterCustomerUseCaseIT {

    @Autowired
    private RegisterCustomerUseCase registerCustomerUseCase;

    @Test
    void deveLancarExcecaoQuandoNomeForNulo() {

        CustomerException exception = assertThrows(CustomerException.class, () -> {
            new Customer("42321973899", null, "joao.saladinha@example.com");
        });

        assertEquals(ErrorsEnum.CLIENTE_NOME_OBRIGATORIO_CADASTRO.getMessage(), exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoEmailForNulo() {
        CustomerException exception = assertThrows(CustomerException.class, () -> {
            new Customer("42321973899", "Joao Saladinha", null);
        });

        assertEquals(ErrorsEnum.CLIENTE_EMAIL_OBRIGATORIO_CADASTRO.getMessage(), exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoCpfForNulo() {
        CustomerException exception = assertThrows(CustomerException.class, () -> {
            new Customer(null, "Joao Saladinha", "joao.saladinha@example.com");
        });

        assertEquals(ErrorsEnum.CLIENTE_CPF_OBRIGATORIO_CADASTRO.getMessage(), exception.getMessage());
    }
}
