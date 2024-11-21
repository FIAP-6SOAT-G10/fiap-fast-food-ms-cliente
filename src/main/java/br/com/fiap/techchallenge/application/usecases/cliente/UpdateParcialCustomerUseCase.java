package br.com.fiap.techchallenge.application.usecases.cliente;

import br.com.fiap.techchallenge.application.gateways.ICustomerRepository;
import br.com.fiap.techchallenge.domain.ErrorsEnum;
import br.com.fiap.techchallenge.domain.entities.customer.Customer;
import br.com.fiap.techchallenge.infra.exception.CustomerException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;

import java.util.regex.Pattern;

public class UpdateParcialCustomerUseCase {

    private final ICustomerRepository clienteRepository;

    public UpdateParcialCustomerUseCase(ICustomerRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Customer updateCustomerData(String id, JsonPatch patch) {
        validateData(id, patch);
        return this.clienteRepository.updateCustomerData(Long.valueOf(id), patch);
    }

    private void validateData(String id, JsonPatch patch) {
        Pattern pattern = Pattern.compile("[^\\d+]");
        if (pattern.matcher(id).find()) {
            throw new CustomerException(ErrorsEnum.CLIENTE_CODIGO_IDENTIFICADOR_INVALIDO);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode request = objectMapper.convertValue(patch, JsonNode.class);

        for (int index = 0; index < request.size(); index++) {

            JsonNode parent = request.get(index);
            if (parent.has("path")) {
                JsonNode path = parent.get("path");

                validateCpf(path, parent);
                validateName(path, parent);
                validateEmail(path, parent);
            }
        }
    }

    private void validateCpf(JsonNode path, JsonNode parent) {
        if (path.asText().equalsIgnoreCase("/cpf")) {
            String cpfContent = parent.get("value").asText();
            if (cpfContent == null || cpfContent.isEmpty()) {
                throw new CustomerException(ErrorsEnum.CLIENTE_CPF_INVALIDO);
            }
        }
    }

    private void validateName(JsonNode path, JsonNode parent) {
        if (path.asText().equalsIgnoreCase("/nome")) {
            String nomeContent = parent.get("value").asText();
            if (nomeContent == null || nomeContent.isEmpty()) {
                throw new CustomerException(ErrorsEnum.CLIENTE_NOME_OBRIGATORIO);
            }
        }
    }

    private void validateEmail(JsonNode path, JsonNode parent) {
        if (path.asText().equalsIgnoreCase("/email")) {
            String emailContent = parent.get("value").asText();
            if (emailContent == null || emailContent.isEmpty()) {
                throw new CustomerException(ErrorsEnum.CLIENTE_EMAIL_OBRIGATORIO);
            }
        }
    }
}
