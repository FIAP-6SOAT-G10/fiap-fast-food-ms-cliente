package br.com.fiap.techchallenge.usecases.application.cliente;

import br.com.fiap.techchallenge.application.gateways.ICustomerRepository;
import br.com.fiap.techchallenge.application.usecases.costumers.UpdateParcialCustomerUseCase;
import br.com.fiap.techchallenge.domain.ErrorsEnum;
import br.com.fiap.techchallenge.domain.entities.customer.Customer;
import br.com.fiap.techchallenge.infra.exception.CustomerException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class UpdateParcialCustomerUseCaseTest {

    private ICustomerRepository customerRepository;
    private UpdateParcialCustomerUseCase updateParcialCustomerUseCase;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        customerRepository = Mockito.mock(ICustomerRepository.class);
        updateParcialCustomerUseCase = new UpdateParcialCustomerUseCase(customerRepository);
        objectMapper = new ObjectMapper();
    }

    @Test
    void updateCustomerDataSuccessfully() throws JsonPatchException, JsonProcessingException {
        JsonPatch patch = objectMapper.readValue("[{\"op\":\"replace\",\"path\":\"/name\",\"value\":\"Joao Saladinha\"}]", JsonPatch.class);
        Customer customer = new Customer("42321973899", "Joao Saladinha", "Joao.Saladinha@example.com");
        when(customerRepository.updateCustomerData(1L, patch)).thenReturn(customer);

        Customer updatedCustomer = updateParcialCustomerUseCase.updateCustomerData("1", patch);

        assertEquals("Joao Saladinha", updatedCustomer.getName());
    }

    @Test
    void updateCustomerDataThrowsExceptionWhenIdIsInvalid() throws JsonPatchException, JsonProcessingException {
        JsonPatch patch = objectMapper.readValue("[{\"op\":\"replace\",\"path\":\"/name\",\"value\":\"Joao Saladinha\"}]", JsonPatch.class);

        assertThrows(CustomerException.class, () -> updateParcialCustomerUseCase.updateCustomerData("invalid_id", patch), ErrorsEnum.CLIENTE_CODIGO_IDENTIFICADOR_INVALIDO.getMessage());
    }

    @Test
    void updateCustomerDataThrowsExceptionWhenCpfIsInvalid() throws JsonPatchException, JsonProcessingException {
        JsonPatch patch = objectMapper.readValue("[{\"op\":\"replace\",\"path\":\"/cpf\",\"value\":\"\"}]", JsonPatch.class);

        assertThrows(CustomerException.class, () -> updateParcialCustomerUseCase.updateCustomerData("1", patch), ErrorsEnum.CLIENTE_CPF_INVALIDO.getMessage());
    }

    @Test
    void updateCustomerDataThrowsExceptionWhenNameIsInvalid() throws JsonPatchException, JsonProcessingException {
        JsonPatch patch = objectMapper.readValue("[{\"op\":\"replace\",\"path\":\"/nome\",\"value\":\"\"}]", JsonPatch.class);

        assertThrows(CustomerException.class, () -> updateParcialCustomerUseCase.updateCustomerData("1", patch), ErrorsEnum.CLIENTE_NOME_OBRIGATORIO.getMessage());
    }

    @Test
    void updateCustomerDataThrowsExceptionWhenEmailIsInvalid() throws JsonPatchException, JsonProcessingException {
        JsonPatch patch = objectMapper.readValue("[{\"op\":\"replace\",\"path\":\"/email\",\"value\":\"\"}]", JsonPatch.class);

        assertThrows(CustomerException.class, () -> updateParcialCustomerUseCase.updateCustomerData("1", patch), ErrorsEnum.CLIENTE_EMAIL_OBRIGATORIO.getMessage());
    }
}
