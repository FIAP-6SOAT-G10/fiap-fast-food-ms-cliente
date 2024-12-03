package br.com.fiap.techchallenge.infra.gateways;

import br.com.fiap.techchallenge.domain.ErrorsEnum;
import br.com.fiap.techchallenge.domain.entities.customer.Customer;
import br.com.fiap.techchallenge.infra.dataproviders.database.persistence.CustomerEntityRepository;
import br.com.fiap.techchallenge.infra.dataproviders.database.persistence.entities.CustomerEntity;
import br.com.fiap.techchallenge.infra.exception.CustomerAlreadyExistsException;
import br.com.fiap.techchallenge.infra.exception.CustomerException;
import br.com.fiap.techchallenge.infra.mapper.CustomerMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CustomerRepositoryTest {

    @Mock
    private CustomerEntityRepository customerEntityRepository;

    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void updateCustomerData_ShouldUpdateCustomerData_WhenCustomerExists() {
        Customer customer = new Customer(1L, "42321973898", "Joao Saladinha", "joao.saladinha@email.com");
        CustomerEntity customerEntity = new CustomerEntity(1L, "42321973898", "Joao Saladinha", "joao.saladinha@email.com");

        when(customerEntityRepository.findById(1L)).thenReturn(Optional.of(customerEntity));
        when(customerMapper.fromDomainToEntity(customer)).thenReturn(customerEntity);
        when(customerEntityRepository.saveAndFlush(customerEntity)).thenReturn(customerEntity);
        when(customerMapper.fromEntityToDomain(customerEntity)).thenReturn(customer);

        Customer updatedCustomer = customerRepository.updateCustomerData(1L, customer);

        assertNotNull(updatedCustomer);
        assertEquals(customer.getId(), updatedCustomer.getId());
        verify(customerEntityRepository, times(1)).findById(1L);
        verify(customerEntityRepository, times(1)).saveAndFlush(customerEntity);
    }

    @Test
    void updateCustomerData_ShouldThrowException_WhenCustomerDoesNotExist() {
        Customer customer = new Customer(1L, "42321973898", "Joao Saladinha", "joao.saladinha@email.com");

        when(customerEntityRepository.findById(1L)).thenReturn(Optional.empty());

        CustomerException exception = assertThrows(CustomerException.class, () -> customerRepository.updateCustomerData(1L, customer));
        assertEquals(ErrorsEnum.CLIENTE_FALHA_GENERICA.getMessage(), exception.getMessage());
    }

    @Test
    void updateCustomer_ShouldUpdateCustomer_WhenCustomerExists() {
        Customer customer = new Customer(1L, "42321973898", "Joao Saladinha", "joao.saladinha@email.com");
        CustomerEntity customerEntity = new CustomerEntity(1L, "42321973898", "Joao Saladinha", "joao.saladinha@email.com");

        when(customerEntityRepository.findById(1L)).thenReturn(Optional.of(customerEntity));
        when(customerMapper.fromDomainToEntity(customer)).thenReturn(customerEntity);
        when(customerEntityRepository.saveAndFlush(customerEntity)).thenReturn(customerEntity);
        when(customerMapper.fromEntityToDomain(customerEntity)).thenReturn(customer);

        Customer updatedCustomer = customerRepository.updateCustomer(1L, customer);

        assertNotNull(updatedCustomer);
        assertEquals(customer.getId(), updatedCustomer.getId());
        verify(customerEntityRepository, times(1)).findById(1L);
        verify(customerEntityRepository, times(1)).saveAndFlush(customerEntity);
    }

    @Test
    void updateCustomer_ShouldThrowException_WhenCustomerDoesNotExist() {
        Customer customer = new Customer(1L, "42321973898", "Joao Saladinha", "joao.saladinha@email.com");

        when(customerEntityRepository.findById(1L)).thenReturn(Optional.empty());

        CustomerException exception = assertThrows(CustomerException.class, () -> customerRepository.updateCustomer(1L, customer));
        assertEquals(ErrorsEnum.CLIENTE_CODIGO_IDENTIFICADOR_INVALIDO.getMessage(), exception.getMessage());
    }

    @Test
    void saveCustomer_ShouldSaveCustomer_WhenCustomerDoesNotExist() {
        Customer customer = new Customer(1L, "42321973898", "Joao Saladinha", "joao.saladinha@email.com");
        CustomerEntity customerEntity = new CustomerEntity(1L, "42321973898", "Joao Saladinha", "joao.saladinha@email.com");

        when(customerEntityRepository.findByCpf(customer.getCpf())).thenReturn(Optional.empty());
        when(customerMapper.fromDomainToEntity(customer)).thenReturn(customerEntity);
        when(customerEntityRepository.saveAndFlush(customerEntity)).thenReturn(customerEntity);
        when(customerMapper.fromEntityToDomain(customerEntity)).thenReturn(customer);

        Customer savedCustomer = customerRepository.saveCustomer(customer);

        assertNotNull(savedCustomer);
        assertEquals(customer.getId(), savedCustomer.getId());
        verify(customerEntityRepository, times(1)).findByCpf(customer.getCpf());
        verify(customerEntityRepository, times(1)).saveAndFlush(customerEntity);
    }

    @Test
    void saveCustomer_ShouldThrowException_WhenCustomerAlreadyExists() {
        Customer customer = new Customer(1L, "42321973898", "Joao Saladinha", "joao.saladinha@email.com");
        CustomerEntity customerEntity = new CustomerEntity(1L, "42321973898", "Joao Saladinha", "joao.saladinha@email.com");

        when(customerEntityRepository.findByCpf(customer.getCpf())).thenReturn(Optional.of(customerEntity));

        CustomerAlreadyExistsException exception = assertThrows(CustomerAlreadyExistsException.class, () -> customerRepository.saveCustomer(customer));
        assertEquals(ErrorsEnum.CLIENTE_JA_CADASTRADO.getMessage(), exception.getMessage());
    }

    @Test
    void listCustomers_ShouldReturnCustomers_WhenCustomersExist() {
        CustomerEntity customerEntity = new CustomerEntity(1L, "42321973898", "Joao Saladinha", "joao.saladinha@email.com");
        List<CustomerEntity> customerEntities = List.of(customerEntity);
        Customer customer = new Customer(1L, "42321973898", "Joao Saladinha", "joao.saladinha@email.com");

        when(customerEntityRepository.findAll()).thenReturn(customerEntities);
        when(customerMapper.fromListEntityToListDomain(customerEntities)).thenReturn(List.of(customer));

        List<Customer> customers = customerRepository.listCustomers(null, null);

        assertNotNull(customers);
        assertFalse(customers.isEmpty());
        assertEquals(1, customers.size());
        verify(customerEntityRepository, times(1)).findAll();
    }

    @Test
    void findByCpf_ShouldReturnCustomer_WhenCustomerExists() {
        CustomerEntity customerEntity = new CustomerEntity(1L, "42321973898", "Joao Saladinha", "joao.saladinha@email.com");
        Customer customer = new Customer(1L, "42321973898", "Joao Saladinha", "joao.saladinha@email.com");

        when(customerEntityRepository.findByCpf("42321973898")).thenReturn(Optional.of(customerEntity));
        when(customerMapper.fromEntityToDomain(customerEntity)).thenReturn(customer);

        Optional<Customer> foundCustomer = customerRepository.findByCpf("42321973898");

        assertTrue(foundCustomer.isPresent());
        assertEquals(customer.getId(), foundCustomer.get().getId());
        verify(customerEntityRepository, times(1)).findByCpf("42321973898");
    }

    @Test
    void findById_ShouldReturnCustomer_WhenCustomerExists() {
        CustomerEntity customerEntity = new CustomerEntity(1L, "42321973898", "Joao Saladinha", "joao.saladinha@email.com");
        Customer customer = new Customer(1L, "42321973898", "Joao Saladinha", "joao.saladinha@email.com");

        when(customerEntityRepository.findById(1L)).thenReturn(Optional.of(customerEntity));
        when(customerMapper.fromEntityToDomain(customerEntity)).thenReturn(customer);

        Optional<Customer> foundCustomer = customerRepository.findById(1L);

        assertTrue(foundCustomer.isPresent());
        assertEquals(customer.getId(), foundCustomer.get().getId());
        verify(customerEntityRepository, times(1)).findById(1L);
    }

    @Test
    void findById_ShouldNotReturnCustomer_WhenCustomerNotExists() {
        CustomerEntity customerEntity = new CustomerEntity(1L, "42321973898", "Joao Saladinha", "joao.saladinha@email.com");
        Customer customer = new Customer(1L, "42321973898", "Joao Saladinha", "joao.saladinha@email.com");

        when(customerEntityRepository.findById(1L)).thenThrow(new CustomerException(ErrorsEnum.CLIENTE_FALHA_GENERICA.getMessage()));
        when(customerMapper.fromEntityToDomain(customerEntity)).thenReturn(customer);

        CustomerException exception = assertThrows(CustomerException.class, () -> customerRepository.updateCustomerData(1L, customer));
        assertEquals(ErrorsEnum.CLIENTE_FALHA_GENERICA.getMessage(), exception.getMessage());
    }
}
