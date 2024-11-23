package br.com.fiap.techchallenge.usecases.application.cliente;

import br.com.fiap.techchallenge.application.usecases.costumers.UpdateCustomerUseCase;
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
class UpdateCustomerUseCaseIT {

    @Autowired
    private UpdateCustomerUseCase updateCustomerUseCase;

    @Test
    void deveLancarExcecaoQuandoIdForInvalido() {
        Customer customer = new Customer("42321973899", "Joao Saladinha", "joao.saladinha@example.com");

        assertThrows(NumberFormatException.class, () -> updateCustomerUseCase.updateCustomers("invalid_id", customer));
    }

    @Test
    void deveLancarExcecaoQuandoClienteForNulo() {
        CustomerException exception = assertThrows(CustomerException.class, () -> {
            updateCustomerUseCase.updateCustomers("1", null);
        });

        assertEquals(ErrorsEnum.CLIENTE_CPF_NAO_EXISTE.getMessage(), exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoClienteNaoForEncontrado() {
        Customer customer = new Customer("42321973899", "Joao Saladinha", "joao.saladinha@example.com");

        CustomerException exception = assertThrows(CustomerException.class, () -> {
            updateCustomerUseCase.updateCustomers("1", customer);
        });

        assertEquals(ErrorsEnum.CLIENTE_CODIGO_IDENTIFICADOR_INVALIDO.getMessage(), exception.getMessage());
    }
}
