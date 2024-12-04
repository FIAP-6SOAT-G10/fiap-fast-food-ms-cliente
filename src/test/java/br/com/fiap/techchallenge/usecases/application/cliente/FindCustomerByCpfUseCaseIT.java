package br.com.fiap.techchallenge.usecases.application.cliente;

import br.com.fiap.techchallenge.application.usecases.costumers.FindCustomerByCpfUseCase;
import br.com.fiap.techchallenge.infra.exception.CustomerException;
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
class FindCustomerByCpfUseCaseIT {

    @Autowired
    private FindCustomerByCpfUseCase findCustomerByCpfUseCase;

    @Test
    void deveLancarExcecaoQuandoCpfNaoExistir() {
        String cpf = "00000000000";

        CustomerException exception = assertThrows(CustomerException.class, () -> {
            findCustomerByCpfUseCase.findByCpf(cpf);
        });

        assertEquals("CPF informado não existe", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoCpfForNulo() {
        CustomerException exception = assertThrows(CustomerException.class, () -> {
            findCustomerByCpfUseCase.findByCpf(null);
        });

        assertEquals("CPF informado não existe", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoCpfForVazio() {
        CustomerException exception = assertThrows(CustomerException.class, () -> {
            findCustomerByCpfUseCase.findByCpf("");
        });

        assertEquals("CPF informado não existe", exception.getMessage());
    }
}
