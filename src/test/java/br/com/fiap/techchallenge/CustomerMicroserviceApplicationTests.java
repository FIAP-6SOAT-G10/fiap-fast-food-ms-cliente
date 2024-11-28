package br.com.fiap.techchallenge;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
    "logging.level.org.springframework=DEBUG",
    "logging.level.br.com.fiap.techchallenge=DEBUG"
})
class CustomerMicroserviceApplicationTests {

    @Test
    void contextLoads() {
    }

}
