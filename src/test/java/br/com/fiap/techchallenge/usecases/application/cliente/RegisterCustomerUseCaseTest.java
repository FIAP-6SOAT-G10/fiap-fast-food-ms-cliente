package br.com.fiap.techchallenge.usecases.application.cliente;

import br.com.fiap.techchallenge.application.gateways.ICustomerRepository;
import br.com.fiap.techchallenge.application.usecases.costumers.RegisterCustomerUseCase;
import br.com.fiap.techchallenge.domain.ErrorsEnum;
import br.com.fiap.techchallenge.domain.entities.customer.Customer;
import br.com.fiap.techchallenge.infra.exception.CustomerException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class RegisterCustomerUseCaseTest {

    private ICustomerRepository customerRepository;
    private RegisterCustomerUseCase registerCustomerUseCase;

    @BeforeEach
    void setUp() {
        customerRepository = Mockito.mock(ICustomerRepository.class);
        registerCustomerUseCase = new RegisterCustomerUseCase(customerRepository);
    }

    @Test
    void saveCustomerSuccessfully() {
        Customer customer = new Customer("42321973899", "Joao Saladinha", "joao.saladinha@example.com");
        when(customerRepository.saveCustomer(customer)).thenReturn(customer);

        Customer savedCustomer = registerCustomerUseCase.saveCustomer(customer);

        assertEquals("Joao Saladinha", savedCustomer.getName());
    }

    @Test
    void saveCustomerThrowsExceptionWhenNameIsNull() {
        CustomerException exception = assertThrows(CustomerException.class, () -> {
            new Customer("42321973899", null, "joao.saladinha@example.com");
        });

        assertEquals(ErrorsEnum.CLIENTE_NOME_OBRIGATORIO_CADASTRO.getMessage(), exception.getMessage());
    }

    @Test
    void saveCustomerThrowsExceptionWhenEmailIsNull() {
        CustomerException exception = assertThrows(CustomerException.class, () -> {
            new Customer("42321973899", "Joao Saladinha", null);
        });

        assertEquals(ErrorsEnum.CLIENTE_EMAIL_OBRIGATORIO_CADASTRO.getMessage(), exception.getMessage());
    }

    @Test
    void saveCustomerThrowsExceptionWhenCpfIsNull() {
        CustomerException exception = assertThrows(CustomerException.class, () -> {
            new Customer(null, "Joao Saladinha", "joao.saladinha@example.com");
        });

        assertEquals(ErrorsEnum.CLIENTE_CPF_OBRIGATORIO_CADASTRO.getMessage(), exception.getMessage());
    }

    @Test
    void saveCustomerThrowsExceptionWhenCpfIsAlreadyRegistered() {
        Customer customer = new Customer("42321973899", "Joao Saladinha", "joao.saladinha@example.com");
        when(customerRepository.findByCpf("42321973899")).thenReturn(Optional.of(customer));

        assertThrows(CustomerException.class, () -> registerCustomerUseCase.saveCustomer(customer), ErrorsEnum.CLIENTE_JA_CADASTRADO.getMessage());
    }
}
