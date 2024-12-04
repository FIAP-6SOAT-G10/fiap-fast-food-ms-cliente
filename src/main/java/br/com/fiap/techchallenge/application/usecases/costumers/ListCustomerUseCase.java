package br.com.fiap.techchallenge.application.usecases.costumers;

import br.com.fiap.techchallenge.application.gateways.ICustomerRepository;
import br.com.fiap.techchallenge.domain.entities.customer.Customer;

import java.util.List;

public class ListCustomerUseCase {

    private final ICustomerRepository customerRepository;

    public ListCustomerUseCase(ICustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> listCustomers(String email, String cpf) {
        return this.customerRepository.listCustomers(email, cpf);
    }
}
