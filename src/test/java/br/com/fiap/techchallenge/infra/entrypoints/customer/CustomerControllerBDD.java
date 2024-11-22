// src/test/java/br/com/fiap/techchallenge/infra/entrypoints/customer/CustomerControllerBDD.java
package br.com.fiap.techchallenge.infra.entrypoints.customer;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Quando;
import io.cucumber.java.pt.Entao;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;


@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
public class CustomerControllerBDD {

    private static final String ENDPOINT_CUSTOMER = "http://localhost:8080/customers";

    private RequestSpecification request;
    private Response response;

    @Dado("que recebo um identificador de cliente valido")
    public void que_recebo_um_identificador_de_cliente_valido() {
        request = given();
    }

    @Quando("realizar a busca")
    public void realizar_a_busca() {
        response = request.when().get(ENDPOINT_CUSTOMER + "/{id}", 1); // Assuming 1 is a valid ID
    }

    @Quando("o cliente nao existir")
    public void o_cliente_nao_existir() {
        response = request.pathParam("id", 9999).when().get(ENDPOINT_CUSTOMER + "/{id}"); // Assuming 9999 is an invalid ID
    }

    @Entao("os detalhes do cliente nao devem ser retornados")
    public void os_detalhes_do_cliente_nao_devem_ser_retornados() {
        response.then().statusCode(404);
    }

    @Entao("o codigo 404 deve ser apresentado")
    public void o_codigo_404_deve_ser_apresentado() {
        assertEquals(404, response.getStatusCode());
    }
}
