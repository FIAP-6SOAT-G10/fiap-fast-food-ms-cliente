//package br.com.fiap.techchallenge.infra.entrypoints.customer;
//
//import br.com.fiap.techchallenge.infra.entrypoints.rest.customer.model.CustomerDTO;
//import io.cucumber.java.pt.Dado;
//import io.cucumber.java.pt.Quando;
//import io.cucumber.java.pt.Entao;
//import org.junit.platform.suite.api.ConfigurationParameter;
//import org.junit.platform.suite.api.IncludeEngines;
//import org.junit.platform.suite.api.SelectClasspathResource;
//import org.junit.platform.suite.api.Suite;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Import;
//import org.springframework.context.annotation.Profile;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.web.client.RestTemplate;
//
//import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@Suite
//@IncludeEngines("cucumber")
//@SelectClasspathResource("features")
//@ActiveProfiles("bdd-test")
//@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "br.com.fiap.techchallenge.infra.entrypoints.rest.customer")
//@SpringBootTest
//public class CustomerRegisterBDD {
//
//    @Autowired
//    private RestTemplate restTemplate;
//
//    private CustomerDTO customerDTO;
//    private ResponseEntity<Void> response;
//
//    @Dado("que eu tenho dados do cliente com nome {string}, email {string}, e cpf {string}")
//    public void que_eu_tenho_dados_do_cliente(String nome, String email, String cpf) {
//        customerDTO = new CustomerDTO(null, cpf, nome, email);
//    }
//
//    @Quando("eu registrar o cliente")
//    public void eu_registrar_o_cliente() {
//        String url = "http://localhost:8080/api/customers";
//        response = restTemplate.postForEntity(url, customerDTO, Void.class);
//    }
//
//    @Entao("o cliente deve ser registrado com sucesso")
//    public void o_cliente_deve_ser_registrado_com_sucesso() {
//        assertEquals(201, response.getStatusCodeValue());
//    }
//
//    @Configuration
//    static class TestConfig {
//
//        @Bean
//        public RestTemplate restTemplate() {
//            return new RestTemplate();
//        }
//    }
//}
//
