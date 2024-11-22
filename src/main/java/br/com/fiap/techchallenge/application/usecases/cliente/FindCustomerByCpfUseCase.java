package br.com.fiap.techchallenge.application.usecases.cliente;

import br.com.fiap.techchallenge.application.gateways.ICustomerRepository;
import br.com.fiap.techchallenge.domain.entities.customer.Customer;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FindCustomerByCpfUseCase {

    private final ICustomerRepository customerRepository;

    public FindCustomerByCpfUseCase(ICustomerRepository clienteRepository) {
        this.customerRepository = clienteRepository;
    }

    public Optional<Customer> findByCpf(String cpf) {
        return customerRepository.findByCpf(cpf);
    }
}
