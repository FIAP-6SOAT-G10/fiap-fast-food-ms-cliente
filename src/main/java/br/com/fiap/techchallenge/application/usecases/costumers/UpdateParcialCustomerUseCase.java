package br.com.fiap.techchallenge.application.usecases.costumers;

import br.com.fiap.techchallenge.application.gateways.ICustomerRepository;
import br.com.fiap.techchallenge.domain.entities.customer.Customer;

public class UpdateParcialCustomerUseCase {

    private final ICustomerRepository clienteRepository;

    public UpdateParcialCustomerUseCase(ICustomerRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Customer updateCustomerData(String id, Customer customer) {
        return this.clienteRepository.updateCustomerData(Long.valueOf(id), customer);
    }
}
