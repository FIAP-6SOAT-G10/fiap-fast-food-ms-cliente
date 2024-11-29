package br.com.fiap.techchallenge.usecases.application.cliente;

import br.com.fiap.techchallenge.application.usecases.costumers.UpdateParcialCustomerUseCase;
import br.com.fiap.techchallenge.domain.ErrorsEnum;
import br.com.fiap.techchallenge.infra.exception.CustomerException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@ActiveProfiles("integration-test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UpdateParcialCustomerUseCaseIT {

    @Autowired
    private UpdateParcialCustomerUseCase updateParcialCustomerUseCase;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveLancarExcecaoQuandoIdForInvalido() throws JsonProcessingException {
        JsonPatch patch = objectMapper.readValue("[{\"op\":\"replace\",\"path\":\"/name\",\"value\":\"Joao Saladinha\"}]", JsonPatch.class);

        CustomerException exception = assertThrows(CustomerException.class, () -> {
            updateParcialCustomerUseCase.updateCustomerData("invalid_id", patch);
        });

        assertEquals(ErrorsEnum.CLIENTE_CODIGO_IDENTIFICADOR_INVALIDO.getMessage(), exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoCpfForInvalido() throws JsonProcessingException {
        JsonPatch patch = objectMapper.readValue("[{\"op\":\"replace\",\"path\":\"/cpf\",\"value\":\"\"}]", JsonPatch.class);

        CustomerException exception = assertThrows(CustomerException.class, () -> {
            updateParcialCustomerUseCase.updateCustomerData("1", patch);
        });

        assertEquals(ErrorsEnum.CLIENTE_CPF_INVALIDO.getMessage(), exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoNomeForInvalido() throws JsonProcessingException {
        JsonPatch patch = objectMapper.readValue("[{\"op\":\"replace\",\"path\":\"/nome\",\"value\":\"\"}]", JsonPatch.class);

        CustomerException exception = assertThrows(CustomerException.class, () -> {
            updateParcialCustomerUseCase.updateCustomerData("1", patch);
        });

        assertEquals(ErrorsEnum.CLIENTE_NOME_OBRIGATORIO.getMessage(), exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoEmailForInvalido() throws JsonProcessingException {
        JsonPatch patch = objectMapper.readValue("[{\"op\":\"replace\",\"path\":\"/email\",\"value\":\"\"}]", JsonPatch.class);

        CustomerException exception = assertThrows(CustomerException.class, () -> {
            updateParcialCustomerUseCase.updateCustomerData("1", patch);
        });

        assertEquals(ErrorsEnum.CLIENTE_EMAIL_OBRIGATORIO.getMessage(), exception.getMessage());
    }
}
