package br.com.fiap.techchallenge.application.usecases.costumers;

import br.com.fiap.techchallenge.application.gateways.ICustomerRepository;
import br.com.fiap.techchallenge.domain.ErrorsEnum;
import br.com.fiap.techchallenge.domain.entities.customer.Customer;
import br.com.fiap.techchallenge.infra.exception.CustomerException;

public class UpdateCustomerUseCase {

    private final ICustomerRepository customerRepository;

    public UpdateCustomerUseCase(ICustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer updateCustomers(String id, Customer customer) {
        if (customer == null) {
            throw new CustomerException(ErrorsEnum.CLIENTE_CPF_NAO_EXISTE);
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
