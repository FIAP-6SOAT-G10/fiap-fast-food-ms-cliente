package br.com.fiap.techchallenge.infra.entrypoints.customer;

import br.com.fiap.techchallenge.infra.entrypoints.rest.customer.model.CustomerDTO;
import io.restassured.RestAssured;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.enableLoggingOfRequestAndResponseIfValidationFails;
import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("integration-test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerControllerIT {

    static final String cpf = RandomStringUtils.randomNumeric(10).toString();

    @BeforeEach
    void setup() {
        RestAssured.port = 8080;
        enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.config = RestAssured.config().redirect(RestAssured.config().getRedirectConfig().followRedirects(true));
    }

    @Nested
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class CustomerTests {

        @Test
        @Order(1)
        void deveRetornarNaoEncontradoQuandoCpfNaoExistir() {
            String cpf = "00000000000";

            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .log().all()
                    .when()
                    .get("/api/customers/cpf/{cpf}", cpf)
                    .then()
                    .log().all()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }

        @Test
        @Order(2)
        void deveCadastrarCliente() {

            CustomerDTO customerDTO = new CustomerDTO(null, cpf, "Joao Saladinha", "joao.saladinha@example.com");

            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(customerDTO)
                    .log().all()
                    .when()
                    .post("/api/customers")
                    .then()
                    .log().all()
                    .statusCode(HttpStatus.CREATED.value());
        }

        @Test
        void deveLancarExcecaoAoCadastrarClienteExistente() {
            CustomerDTO customerDTO = new CustomerDTO(null, cpf, "Joao Saladinha", "joao.saladinha@example.com");

            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(customerDTO)
                    .when()
                    .post("/api/customers");

            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(customerDTO)
                    .log().all()
                    .when()
                    .post("/api/customers")
                    .then()
                    .log().all()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }

        @Test
        void deveRetornarClientePorCpf() {

            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .log().all()
                    .when()
                    .get("/api/customers/cpf/{cpf}", cpf)
                    .then()
                    .log().all()
                    .statusCode(HttpStatus.OK.value());
        }

        @Test
        void deveAtualizarCliente() {
            String id = "1";
            String cpf = RandomStringUtils.randomNumeric(10).toString();
            CustomerDTO customerDTO = new CustomerDTO(null, cpf, "Joao Updated", "joao.updated@example.com");

            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(new CustomerDTO(null, "12345678901", "Joao Saladinha", "joao.saladinha@example.com"))
                    .when()
                    .post("/api/customers");

            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(customerDTO)
                    .log().all()
                    .when()
                    .put("/api/customers/{id}", id)
                    .then()
                    .log().all()
                    .statusCode(HttpStatus.NO_CONTENT.value());
        }

        @Test
        void deveRetornarNaoEncontradoQuandoClienteNaoExistir() {
            String id = "9999";
            CustomerDTO customerDTO = new CustomerDTO(null, "12345678901", "Joao Updated", "joao.updated@example.com");

            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(customerDTO)
                    .log().all()
                    .when()
                    .put("/api/customers/{id}", id)
                    .then()
                    .log().all()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }

        @Test
        void deveListarTodosOsClientes() {
            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when()
                    .post("/api/customers");

            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .log().all()
                    .when()
                    .get("/api/customers")
                    .then()
                    .log().all()
                    .statusCode(HttpStatus.OK.value());
        }
    }
}
