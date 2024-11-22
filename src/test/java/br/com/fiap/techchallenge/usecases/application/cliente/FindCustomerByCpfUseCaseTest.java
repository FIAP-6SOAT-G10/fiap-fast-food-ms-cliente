package br.com.fiap.techchallenge.usecases.application.cliente;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import br.com.fiap.techchallenge.application.gateways.ICustomerRepository;
import br.com.fiap.techchallenge.application.usecases.cliente.FindCustomerByCpfUseCase;
import br.com.fiap.techchallenge.domain.entities.customer.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Optional;

class FindCustomerByCpfUseCaseTest {

    private ICustomerRepository customerRepository;
    private FindCustomerByCpfUseCase findCustomerByIdUseCase;

    @BeforeEach
    void setUp() {
        customerRepository = mock(ICustomerRepository.class);
        findCustomerByIdUseCase = new FindCustomerByCpfUseCase(customerRepository);
    }

    @Test
    void findByCpfReturnsCustomerWhenCpfExists() {
        Customer customer = new Customer("12345678901", "Joao Saladinha", "joao.saladinha@example.com");
        when(customerRepository.findByCpf("12345678901")).thenReturn(Optional.of(customer));

        Optional<Customer> result = findCustomerByIdUseCase.findByCpf("12345678901");

        assertTrue(result.isPresent());
        assertEquals("Joao Saladinha", result.get().getName());
    }

    @Test
    void findByCpfReturnsEmptyWhenCpfDoesNotExist() {
        when(customerRepository.findByCpf("12345678901")).thenReturn(Optional.empty());

        Optional<Customer> result = findCustomerByIdUseCase.findByCpf("12345678901");

        assertFalse(result.isPresent());
    }

    @Test
    void findByCpfReturnsEmptyWhenCpfIsNull() {
        Optional<Customer> result = findCustomerByIdUseCase.findByCpf(null);

        assertFalse(result.isPresent());
    }

    @Test
    void findByCpfReturnsEmptyWhenCpfIsEmpty() {
        Optional<Customer> result = findCustomerByIdUseCase.findByCpf("");

        assertFalse(result.isPresent());
    }
}
