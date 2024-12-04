package br.com.fiap.techchallenge.usecases.application.cliente;

import br.com.fiap.techchallenge.application.gateways.ICustomerRepository;
import br.com.fiap.techchallenge.application.usecases.costumers.UpdateCustomerUseCase;
import br.com.fiap.techchallenge.domain.entities.customer.Customer;
import br.com.fiap.techchallenge.infra.exception.CustomerException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class UpdateCustomerUseCaseTest {

    private ICustomerRepository customerRepository;
    private UpdateCustomerUseCase updateCustomerUseCase;

    @BeforeEach
    void setUp() {
        customerRepository = Mockito.mock(ICustomerRepository.class);
        updateCustomerUseCase = new UpdateCustomerUseCase(customerRepository);
    }

    @Test
    void updateCustomerSuccessfully() {
        Customer customer = new Customer("42321973899", "Joao Saladinha", "joao.saladinha@example.com");
        when(customerRepository.updateCustomer(1L, customer)).thenReturn(customer);

        Customer updatedCustomer = updateCustomerUseCase.updateCustomers("1", customer);

        assertEquals("Joao Saladinha", updatedCustomer.getName());
    }

    @Test
    void updateCustomerThrowsExceptionWhenIdIsInvalid() {
        Customer customer = new Customer("42321973899", "Joao Saladinha", "joao.saladinha@example.com");

        assertThrows(NumberFormatException.class, () -> updateCustomerUseCase.updateCustomers("invalid_id", customer));
    }

    @Test
    void updateCustomerThrowsExceptionWhenCustomerIsNull() {
        assertThrows(CustomerException.class, () -> updateCustomerUseCase.updateCustomers("1", null));
    }

    @Test
    void updateCustomerThrowsExceptionWhenCustomerNotFound() {
        Customer customer = new Customer("42321973899", "Joao Saladinha", "joao.saladinha@example.com");
        when(customerRepository.updateCustomer(1L, customer)).thenThrow(new IllegalArgumentException("Customer not found"));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> updateCustomerUseCase.updateCustomers("1", customer));
        assertEquals("Customer not found", exception.getMessage());
    }
}
