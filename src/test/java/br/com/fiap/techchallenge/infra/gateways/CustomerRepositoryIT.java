package br.com.fiap.techchallenge.infra.gateways;

import br.com.fiap.techchallenge.domain.ErrorsEnum;
import br.com.fiap.techchallenge.infra.dataproviders.database.persistence.CustomerEntityRepository;
import br.com.fiap.techchallenge.infra.dataproviders.database.persistence.entities.CustomerEntity;
import br.com.fiap.techchallenge.infra.exception.CustomerException;
import br.com.fiap.techchallenge.infra.mapper.CustomerMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Transactional
@SpringBootTest
@ActiveProfiles("integration-test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CustomerRepositoryIT {

    @MockBean
    private CustomerEntityRepository customerEntityRepository;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void deveLancarExcecaoQuandoIdForInvalido() {
        JsonPatch patch = mock(JsonPatch.class);

        CustomerException exception = assertThrows(CustomerException.class, () -> {
            customerRepository.updateCustomerData(1L, patch);
        });

        assertEquals(ErrorsEnum.CLIENTE_FALHA_GENERICA.getMessage(), exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoJsonPatchExceptionOcorrer() throws JsonPatchException {
        CustomerEntity customerEntity = new CustomerEntity();
        JsonPatch patch = mock(JsonPatch.class);
        when(customerEntityRepository.findById(1L)).thenReturn(Optional.of(customerEntity));
        when(patch.apply(any(JsonNode.class))).thenThrow(new JsonPatchException("Patch error"));

        CustomerException exception = assertThrows(CustomerException.class, () -> {
            customerRepository.updateCustomerData(1L, patch);
        });

        assertEquals(ErrorsEnum.CLIENTE_FALHA_DURANTE_ATUALIZACAO.getMessage(), exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoClienteNaoForEncontrado() {
        when(customerEntityRepository.findById(1L)).thenReturn(Optional.empty());

        CustomerException exception = assertThrows(CustomerException.class, () -> {
            customerRepository.updateCustomerData(1L, mock(JsonPatch.class));
        });

        assertEquals(ErrorsEnum.CLIENTE_FALHA_GENERICA.getMessage(), exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoErroGenericoOcorrer() {
        when(customerEntityRepository.findById(1L)).thenThrow(new RuntimeException("Generic exception"));

        CustomerException exception = assertThrows(CustomerException.class, () -> {
            customerRepository.updateCustomerData(1L, mock(JsonPatch.class));
        });

        assertEquals(ErrorsEnum.CLIENTE_FALHA_GENERICA.getMessage(), exception.getMessage());
    }
}
