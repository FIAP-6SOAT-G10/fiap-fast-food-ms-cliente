package br.com.fiap.techchallenge.usecases.application.cliente;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import br.com.fiap.techchallenge.application.gateways.ICustomerRepository;
import br.com.fiap.techchallenge.application.usecases.costumers.FindCustomerByCpfUseCase;
import br.com.fiap.techchallenge.domain.entities.customer.Customer;
import br.com.fiap.techchallenge.infra.exception.CustomerException;
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
        when(customerRepository.findByCpf("12345678901")).thenThrow(new CustomerException("CPF informado não existe"));

        CustomerException exception = assertThrows(CustomerException.class, () -> {
            findCustomerByIdUseCase.findByCpf("12345678901");
        });

        assertEquals("CPF informado não existe", exception.getMessage());
    }

    @Test
    void findByCpfReturnsEmptyWhenCpfIsNull() {
        CustomerException exception = assertThrows(CustomerException.class, () -> {
            findCustomerByIdUseCase.findByCpf(null);
        });

        assertEquals("CPF informado não existe", exception.getMessage());
    }

    @Test
    void findByCpfReturnsEmptyWhenCpfIsEmpty() {
        CustomerException exception = assertThrows(CustomerException.class, () -> {
            findCustomerByIdUseCase.findByCpf("");
        });

        assertEquals("CPF informado não existe", exception.getMessage());
    }
}
