package br.com.fiap.techchallenge.application.usecases.cliente;

import br.com.fiap.techchallenge.application.gateways.ICustomerRepository;
import br.com.fiap.techchallenge.domain.entities.customer.Customer;

import java.util.List;
import java.util.Optional;

public class ListCustomerUseCase {

    private final ICustomerRepository customerRepository;

    public ListCustomerUseCase(ICustomerRepository clienteRepository) {
        this.customerRepository = clienteRepository;
    }

    public List<Customer> listCustomers(String email, String cpf) {
        return this.customerRepository.listCustomers(email, cpf);
    }

    public Optional<Customer> findByCpf(String cpf) {
        return customerRepository.findByCpf(cpf);
    }
}
