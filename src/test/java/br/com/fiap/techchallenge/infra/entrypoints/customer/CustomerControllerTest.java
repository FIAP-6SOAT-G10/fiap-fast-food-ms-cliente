package br.com.fiap.techchallenge.infra.entrypoints.customer;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import br.com.fiap.techchallenge.application.usecases.costumers.*;
import br.com.fiap.techchallenge.domain.ErrorsEnum;
import br.com.fiap.techchallenge.domain.entities.customer.Customer;
import br.com.fiap.techchallenge.infra.entrypoints.rest.customer.CustomerController;
import br.com.fiap.techchallenge.infra.entrypoints.rest.customer.model.CustomerDTO;
import br.com.fiap.techchallenge.infra.exception.CustomerAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

class CustomerControllerTest {

    private RegisterCustomerUseCase registerCustomerUseCase;
    private ListCustomerUseCase listCustomerUseCase;
    private FindCustomerByCpfUseCase findCustomerByIdUseCase;
    private UpdateParcialCustomerUseCase updateParcialCustomerUseCase;
    private UpdateCustomerUseCase updateCustomerUseCase;
    private CustomerController customerController;

    @BeforeEach
    void setUp() {
        registerCustomerUseCase = mock(RegisterCustomerUseCase.class);
        listCustomerUseCase = mock(ListCustomerUseCase.class);
        findCustomerByIdUseCase = mock(FindCustomerByCpfUseCase.class);
        updateParcialCustomerUseCase = mock(UpdateParcialCustomerUseCase.class);
        updateCustomerUseCase = mock(UpdateCustomerUseCase.class);
        customerController = new CustomerController(registerCustomerUseCase, listCustomerUseCase, findCustomerByIdUseCase, updateParcialCustomerUseCase, updateCustomerUseCase);
    }

    @Test
    void registerCustomerCreatesNewCustomer() {
        CustomerDTO customerDTO = new CustomerDTO(null, "12345678901", "Joao Saladinha", "joao.saladinha@example.com");
        Customer customer = new Customer("12345678901", "Joao Saladinha", "joao.saladinha@example.com");
        when(registerCustomerUseCase.saveCustomer(any(Customer.class))).thenReturn(customer);

        ResponseEntity<Void> response = customerController.registerCustomer(customerDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void registerCustomerThrowsExceptionWhenCustomerAlreadyExists() {
        CustomerDTO customerDTO = new CustomerDTO(null, "12345678901", "Joao Saladinha", "joao.saladinha@example.com");
        doThrow(new CustomerAlreadyExistsException(ErrorsEnum.CLIENTE_JA_CADASTRADO)).when(registerCustomerUseCase).saveCustomer(any(Customer.class));

        ResponseEntity<Void> response = customerController.registerCustomer(customerDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void listAllCustomersReturnsCustomers() {
        Customer customer = new Customer("12345678901", "Joao Saladinha", "joao.saladinha@example.com");
        when(listCustomerUseCase.listCustomers(null, null)).thenReturn(Collections.singletonList(customer));

        ResponseEntity<List<CustomerDTO>> response = customerController.listAllCustomers(null, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void listAllCustomersReturnsNoContentWhenNoCustomersFound() {
        when(listCustomerUseCase.listCustomers(null, null)).thenReturn(Collections.emptyList());

        ResponseEntity<List<CustomerDTO>> response = customerController.listAllCustomers(null, null);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void getCustomerByCpfReturnsCustomerDetails() {
        Customer customer = new Customer("12345678901", "Joao Saladinha", "joao.saladinha@example.com");
        when(findCustomerByIdUseCase.findByCpf("12345678901")).thenReturn(Optional.of(customer));

        ResponseEntity<CustomerDTO> response = customerController.getCustomerByCpf("12345678901");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("12345678901", response.getBody().getCpf());
    }

    @Test
    void getCustomerByCpfReturnsNotFoundWhenCustomerDoesNotExist() {
        when(findCustomerByIdUseCase.findByCpf("12345678901")).thenReturn(Optional.empty());

        ResponseEntity<CustomerDTO> response = customerController.getCustomerByCpf("12345678901");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
