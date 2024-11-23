package br.com.fiap.techchallenge.usecases.application.cliente;

import br.com.fiap.techchallenge.application.gateways.ICustomerRepository;
import br.com.fiap.techchallenge.application.usecases.costumers.ListCustomerUseCase;
import br.com.fiap.techchallenge.domain.entities.customer.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


class ListCustomerUseCaseTest {

    private ICustomerRepository customerRepository;
    private ListCustomerUseCase listCustomerUseCase;

    @BeforeEach
    void setUp() {
        customerRepository = Mockito.mock(ICustomerRepository.class);
        listCustomerUseCase = new ListCustomerUseCase(customerRepository);
    }

    @Test
    void listCustomersReturnsCustomers() {
        Customer customer = new Customer("12345678901", "Joao Saladinha", "joao.saladinha@example.com");
        when(customerRepository.listCustomers("joao.saladinha@example.com", "12345678901"))
                .thenReturn(Collections.singletonList(customer));

        List<Customer> customers = listCustomerUseCase.listCustomers("joao.saladinha@example.com", "12345678901");

        assertEquals(1, customers.size());
        assertEquals("Joao Saladinha", customers.get(0).getName());
    }

    @Test
    void listCustomersReturnsEmptyListWhenNoCustomersFound() {
        when(customerRepository.listCustomers("nonexistent@example.com", "00000000000"))
                .thenReturn(Collections.emptyList());

        List<Customer> customers = listCustomerUseCase.listCustomers("nonexistent@example.com", "00000000000");

        assertEquals(0, customers.size());
    }

    @Test
    void listCustomersReturnsAllCustomersWhenNoFiltersProvided() {
        Customer customer1 = new Customer("Joao Saladinha", "joao.saladinha@example.com", "12345678901");
        Customer customer2 = new Customer("Jane Saladinha", "jane.doe@example.com", "10987654321");
        when(customerRepository.listCustomers(null, null))
                .thenReturn(List.of(customer1, customer2));

        List<Customer> customers = listCustomerUseCase.listCustomers(null, null);

        assertEquals(2, customers.size());
    }
}
