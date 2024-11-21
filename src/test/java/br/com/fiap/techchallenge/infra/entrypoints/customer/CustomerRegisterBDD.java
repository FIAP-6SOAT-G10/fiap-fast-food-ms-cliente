package br.com.fiap.techchallenge.infra.entrypoints.rest.customer;

import br.com.fiap.techchallenge.infra.entrypoints.rest.customer.model.CustomerDTO;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Quando;
import io.cucumber.java.pt.Entao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CustomerRegisterIT {

    @Autowired
    private RestTemplate restTemplate;

    private CustomerDTO customerDTO;
    private ResponseEntity<Void> response;

    @Dado("que eu tenho dados do cliente com nome {string}, email {string}, e cpf {string}")
    public void que_eu_tenho_dados_do_cliente(String nome, String email, String cpf) {
        customerDTO = new CustomerDTO(null, cpf, nome, email);
    }

    @Quando("eu registrar o cliente")
    public void eu_registrar_o_cliente() {
        String url = "http://localhost:8080/api/customers";
        response = restTemplate.postForEntity(url, customerDTO, Void.class);
    }

    @Entao("o cliente deve ser registrado com sucesso")
    public void o_cliente_deve_ser_registrado_com_sucesso() {
        assertEquals(201, response.getStatusCodeValue());
    }
}
