package br.com.fiap.techchallenge.infra.config;

import br.com.fiap.techchallenge.application.gateways.ICustomerRepository;
import br.com.fiap.techchallenge.application.usecases.cliente.UpdateParcialCustomerUseCase;
import br.com.fiap.techchallenge.application.usecases.cliente.UpdateCustomerUseCase;
import br.com.fiap.techchallenge.application.usecases.cliente.RegisterCustomerUseCase;
import br.com.fiap.techchallenge.application.usecases.cliente.ListCustomerUseCase;
import br.com.fiap.techchallenge.infra.gateways.CustomerRepository;
import br.com.fiap.techchallenge.infra.mapper.CustomerMapper;
import br.com.fiap.techchallenge.infra.dataproviders.database.persistence.CustomerEntityRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomerConfig {

    @Bean
    public ListCustomerUseCase listCustomersUseCase(ICustomerRepository clienteRepository) {
        return new ListCustomerUseCase(clienteRepository);
    }

    @Bean
    public RegisterCustomerUseCase registerCustomerUseCase(ICustomerRepository clienteRepository) {
        return new RegisterCustomerUseCase(clienteRepository);
    }

    @Bean
    public UpdateParcialCustomerUseCase updateParcialCustomerUseCase(ICustomerRepository clienteRepository) {
        return new UpdateParcialCustomerUseCase(clienteRepository);
    }

    @Bean
    public UpdateCustomerUseCase updateCustomerUseCase(ICustomerRepository clienteRepository) {
        return new UpdateCustomerUseCase(clienteRepository);
    }

    @Bean
    public ICustomerRepository customerRepository(CustomerEntityRepository customerEntityRepository, CustomerMapper customerMapper, ObjectMapper objectMapper) {
        return new CustomerRepository(customerEntityRepository, customerMapper, objectMapper);
    }

    @Bean
    public CustomerMapper customerMapper() {
        return new CustomerMapper();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
