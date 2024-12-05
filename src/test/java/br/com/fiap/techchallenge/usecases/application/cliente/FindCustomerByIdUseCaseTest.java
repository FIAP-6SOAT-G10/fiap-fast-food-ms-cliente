package br.com.fiap.techchallenge.usecases.application.cliente;

import br.com.fiap.techchallenge.domain.entities.customer.Customer;
import br.com.fiap.techchallenge.application.usecases.costumers.FindCustomerByCpfUseCase;
import br.com.fiap.techchallenge.application.gateways.ICustomerRepository;
import br.com.fiap.techchallenge.infra.exception.CustomerException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class FindCustomerByIdUseCaseTest {

    @Mock
    private ICustomerRepository customerRepository;

    @InjectMocks
    private FindCustomerByCpfUseCase findCustomerByIdUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById_Success() {
        Long id = 1L;
        Customer customer = new Customer(id, "42321973898", "Joao Saladinha", "joao.saladinha@mail.com");
        when(customerRepository.findById(id)).thenReturn(Optional.of(customer));

        Optional<Customer> result = findCustomerByIdUseCase.findByID(id);

        assertTrue(result.isPresent());
        assertEquals(customer, result.get());
    }

    @Test
    void testFindById_NotFound() {
        Long id = 1L;
        when(customerRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(CustomerException.class, () -> {
            findCustomerByIdUseCase.findByID(id);
        });
    }
}
