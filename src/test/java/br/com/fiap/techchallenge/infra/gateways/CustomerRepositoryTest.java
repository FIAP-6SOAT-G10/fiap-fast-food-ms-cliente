package br.com.fiap.techchallenge.infra.gateways;

import br.com.fiap.techchallenge.domain.ErrorsEnum;
import br.com.fiap.techchallenge.domain.entities.customer.Customer;
import br.com.fiap.techchallenge.infra.dataproviders.database.persistence.CustomerEntityRepository;
import br.com.fiap.techchallenge.infra.dataproviders.database.persistence.entities.CustomerEntity;
import br.com.fiap.techchallenge.infra.exception.CustomerException;
import br.com.fiap.techchallenge.infra.mapper.CustomerMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerRepositoryTest {

    @Mock
    private CustomerEntityRepository customerEntityRepository;

    @Mock
    private CustomerMapper customerMapper;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private CustomerRepository customerRepository;

    @Mock
    private JsonPatch patch;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void updateCustomerDataSuccessfully() throws Exception {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setName("Joao Saladinha");

        JsonNode customerNode = mock(JsonNode.class);
        JsonNode patchedNode = mock(JsonNode.class);

        when(customerEntityRepository.findById(1L)).thenReturn(Optional.of(customerEntity));
        when(objectMapper.convertValue(customerEntity, JsonNode.class)).thenReturn(customerNode);
        when(patch.apply(customerNode)).thenReturn(patchedNode);
        when(objectMapper.treeToValue(patchedNode, CustomerEntity.class)).thenReturn(customerEntity);
        when(customerEntityRepository.saveAndFlush(customerEntity)).thenReturn(customerEntity);
        when(customerMapper.fromEntityToDomain(customerEntity)).thenReturn(new Customer());

        Customer updatedCustomer = customerRepository.updateCustomerData(1L, patch);

        assertNotNull(updatedCustomer);
    }

    @Test
    void updateCustomerDataThrowsExceptionWhenCustomerNotFound() {
        when(customerEntityRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CustomerException.class, () -> customerRepository.updateCustomerData(1L, patch), "CLIENTE_NAO_ENCONTRADO");
    }

    @Test
    void updateCustomerDataThrowsExceptionWhenJsonPatchExceptionOccurs() throws JsonPatchException {
        CustomerEntity customerEntity = new CustomerEntity();
        when(customerEntityRepository.findById(1L)).thenReturn(Optional.of(customerEntity));
        when(patch.apply(any(JsonNode.class))).thenThrow(new JsonPatchException("Patch error"));

        CustomerException exception = assertThrows(CustomerException.class, () -> customerRepository.updateCustomerData(1L, patch));
        assertEquals(ErrorsEnum.CLIENTE_FALHA_GENERICA, exception.getError());
}

    @Test
    public void updateCustomerDataThrowsExceptionWhenGenericExceptionOccurs() {
        JsonPatch patch = mock(JsonPatch.class);

        when(customerEntityRepository.findById(1L)).thenThrow(new RuntimeException("Generic exception"));

        assertThrows(CustomerException.class, () -> {
            customerRepository.updateCustomerData(1L, patch);
        });
    }
}
