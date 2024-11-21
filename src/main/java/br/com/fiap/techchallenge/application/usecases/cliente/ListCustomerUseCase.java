package br.com.fiap.techchallenge.application.usecases.cliente;

import br.com.fiap.techchallenge.application.gateways.ICustomerRepository;
import br.com.fiap.techchallenge.domain.entities.customer.Customer;

import java.util.List;

public class ListCustomerUseCase {

    private final ICustomerRepository clienteRepository;

    public ListCustomerUseCase(ICustomerRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<Customer> listCustomers(String email, String cpf) {
        return this.clienteRepository.listCustomers(email, cpf);
    }
}
