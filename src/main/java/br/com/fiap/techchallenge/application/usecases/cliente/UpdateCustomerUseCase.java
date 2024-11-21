package br.com.fiap.techchallenge.application.usecases.cliente;

import br.com.fiap.techchallenge.application.gateways.ICustomerRepository;
import br.com.fiap.techchallenge.domain.entities.customer.Customer;

public class UpdateCustomerUseCase {

    private final ICustomerRepository customerRepository;

    public UpdateCustomerUseCase(ICustomerRepository clienteRepository) {
        this.customerRepository = clienteRepository;
    }

    public Customer updateCustomers(String id, Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }

        long customerId;
        try {
            customerId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Invalid customer ID");
        }

        return customerRepository.updateCustomer(customerId, customer);
    }
}
