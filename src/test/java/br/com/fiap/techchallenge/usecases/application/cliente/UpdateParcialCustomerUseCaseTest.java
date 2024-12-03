package br.com.fiap.techchallenge.usecases.application.cliente;

import br.com.fiap.techchallenge.application.gateways.ICustomerRepository;
import br.com.fiap.techchallenge.application.usecases.costumers.UpdateParcialCustomerUseCase;
import br.com.fiap.techchallenge.domain.entities.customer.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class UpdateParcialCustomerUseCaseTest {

    private ICustomerRepository customerRepository;
    private UpdateParcialCustomerUseCase updateParcialCustomerUseCase;

    @BeforeEach
    void setUp() {
        customerRepository = Mockito.mock(ICustomerRepository.class);
        updateParcialCustomerUseCase = new UpdateParcialCustomerUseCase(customerRepository);
    }

    @Test
    void updateCustomerDataSuccessfully() {
        Customer customer = new Customer("42321973899", "Joao Saladinha", "Joao.Saladinha@example.com");
        when(customerRepository.updateCustomerData(1L, customer)).thenReturn(customer);

        Customer updatedCustomer = updateParcialCustomerUseCase.updateCustomerData("1", customer);

        assertEquals("Joao Saladinha", updatedCustomer.getName());
    }
}
