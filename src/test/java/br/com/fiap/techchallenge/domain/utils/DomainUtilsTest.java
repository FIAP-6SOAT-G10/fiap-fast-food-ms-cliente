package br.com.fiap.techchallenge.domain.utils;

import br.com.fiap.techchallenge.domain.ErrorsEnum;
import br.com.fiap.techchallenge.infra.exception.CustomerException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DomainUtilsTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void validateData_ShouldThrowException_WhenIdIsInvalid() {
        JsonPatch patch = createPatch("/cpf", "123.456.789-00");
        CustomerException exception = assertThrows(CustomerException.class, () -> DomainUtils.validateData("invalid_id", patch));
        assertEquals(ErrorsEnum.CLIENTE_CODIGO_IDENTIFICADOR_INVALIDO.getMessage(), exception.getMessage());
    }

    @Test
    void validateData_ShouldThrowException_WhenCpfIsInvalid() {
        JsonPatch patch = createPatch("/cpf", "");
        CustomerException exception = assertThrows(CustomerException.class, () -> DomainUtils.validateData("1", patch));
        assertEquals(ErrorsEnum.CLIENTE_CPF_INVALIDO.getMessage(), exception.getMessage());
    }

    @Test
    void validateData_ShouldThrowException_WhenNameIsInvalid() {
        JsonPatch patch = createPatch("/nome", "");
        CustomerException exception = assertThrows(CustomerException.class, () -> DomainUtils.validateData("1", patch));
        assertEquals(ErrorsEnum.CLIENTE_NOME_OBRIGATORIO.getMessage(), exception.getMessage());
    }

    @Test
    void validateData_ShouldThrowException_WhenEmailIsInvalid() {
        JsonPatch patch = createPatch("/email", "");
        CustomerException exception = assertThrows(CustomerException.class, () -> DomainUtils.validateData("1", patch));
        assertEquals(ErrorsEnum.CLIENTE_EMAIL_OBRIGATORIO.getMessage(), exception.getMessage());
    }

    @Test
    void validateData_ShouldNotThrowException_WhenDataIsValid() {
        JsonPatch patch = createPatch("/cpf", "123.456.789-00");
        assertDoesNotThrow(() -> DomainUtils.validateData("1", patch));
    }

    private JsonPatch createPatch(String path, String value) {
        String patchStr = String.format("[{\"op\": \"replace\", \"path\": \"%s\", \"value\": \"%s\"}]", path, value);
        try {
            return objectMapper.readValue(patchStr, JsonPatch.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to create patch", e);
        }
    }
}
