package br.com.fiap.techchallenge.infra.config;

import br.com.fiap.techchallenge.application.gateways.ICustomerRepository;
import br.com.fiap.techchallenge.application.usecases.costumers.UpdateParcialCustomerUseCase;
import br.com.fiap.techchallenge.application.usecases.costumers.UpdateCustomerUseCase;
import br.com.fiap.techchallenge.application.usecases.costumers.RegisterCustomerUseCase;
import br.com.fiap.techchallenge.application.usecases.costumers.ListCustomerUseCase;
import br.com.fiap.techchallenge.infra.gateways.CustomerRepository;
import br.com.fiap.techchallenge.infra.mapper.CustomerMapper;
import br.com.fiap.techchallenge.infra.dataproviders.database.persistence.CustomerEntityRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomerConfig {

    @Bean
    public ListCustomerUseCase listCustomersUseCase(ICustomerRepository customerRepository) {
        return new ListCustomerUseCase(customerRepository);
    }

    @Bean
    public RegisterCustomerUseCase registerCustomerUseCase(ICustomerRepository customerRepository) {
        return new RegisterCustomerUseCase(customerRepository);
    }

    @Bean
    public UpdateParcialCustomerUseCase updateParcialCustomerUseCase(ICustomerRepository customerRepository) {
        return new UpdateParcialCustomerUseCase(customerRepository);
    }

    @Bean
    public UpdateCustomerUseCase updateCustomerUseCase(ICustomerRepository customerRepository) {
        return new UpdateCustomerUseCase(customerRepository);
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
