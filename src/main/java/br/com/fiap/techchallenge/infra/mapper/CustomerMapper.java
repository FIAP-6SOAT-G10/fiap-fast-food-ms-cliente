package br.com.fiap.techchallenge.infra.mapper;

import br.com.fiap.techchallenge.domain.entities.customer.Customer;
import br.com.fiap.techchallenge.infra.entrypoints.rest.customer.model.CustomerDTO;
import br.com.fiap.techchallenge.infra.dataproviders.database.persistence.entities.CustomerEntity;

import java.util.List;

public class CustomerMapper {

    public CustomerEntity fromDomainToEntity(Customer customer) {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setId(customer.getId());
        customerEntity.setCpf(customer.getCpf());
        customerEntity.setName(customer.getName());
        customerEntity.setEmail(customer.getEmail());

        return customerEntity;
    }

    public Customer fromEntityToDomain(CustomerEntity customerEntity) {
        if (customerEntity == null) {
            return null;
        }
        return new Customer(customerEntity.getId(), customerEntity.getCpf(), customerEntity.getName(), customerEntity.getEmail());
    }

    public Customer fromDTOToDomain(CustomerDTO customerDTO) {
        return new Customer(customerDTO.getId(), customerDTO.getCpf(), customerDTO.getName(), customerDTO.getEmail());
    }

    public List<Customer> fromListEntityToListDomain(List<CustomerEntity> clientes) {
        return clientes.stream().map(this::fromEntityToDomain).toList();
    }

    public List<CustomerEntity> fromListDomainToListEntity(List<Customer> customers) {
        return customers.stream().map(this::fromDomainToEntity).toList();
    }
}
