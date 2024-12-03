package br.com.fiap.techchallenge.usecases.application.cliente;

import br.com.fiap.techchallenge.application.usecases.costumers.UpdateParcialCustomerUseCase;
import br.com.fiap.techchallenge.domain.ErrorsEnum;
import br.com.fiap.techchallenge.domain.entities.customer.Customer;
import br.com.fiap.techchallenge.infra.exception.CustomerException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("integration-test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UpdateParcialCustomerUseCaseIT {

    @Autowired
    private UpdateParcialCustomerUseCase updateParcialCustomerUseCase;

    @Test
    void deveLancarExcecaoQuandoIdForInvalido() {
        Customer customer = new Customer("42321973899", "Joao Saladinha", "Joao.Saladinha@example.com");
        NumberFormatException exception = assertThrows(NumberFormatException.class, () -> updateParcialCustomerUseCase.updateCustomerData("invalid_id", customer));
        assertThrows(NumberFormatException.class, () -> updateParcialCustomerUseCase.updateCustomerData("invalid_id", customer));


    }

    @Test
    void deveLancarExcecaoQuandoOcorrerExcecaoGenerica() {
        Customer customer = new Customer("42321973899", "Joao Saladinha", "Joao.Saladinha@example.com");
        CustomerException exception = assertThrows(CustomerException.class, () -> updateParcialCustomerUseCase.updateCustomerData("1", customer));
        assertEquals(ErrorsEnum.CLIENTE_FALHA_GENERICA.getMessage(), exception.getMessage());
    }
}
