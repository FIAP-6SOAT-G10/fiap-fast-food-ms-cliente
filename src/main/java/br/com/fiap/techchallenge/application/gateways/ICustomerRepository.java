package br.com.fiap.techchallenge.application.gateways;

import br.com.fiap.techchallenge.domain.entities.customer.Customer;
import com.github.fge.jsonpatch.JsonPatch;

import java.util.List;
import java.util.Optional;

public interface ICustomerRepository {

    Customer updateCustomerData(Long id, JsonPatch patch);

    Customer updateCustomer(Long id, Customer customer);

    Customer saveCustomer(Customer customer);

    List<Customer> listCustomers(String email, String cpf);

    Optional<Customer> findByCpf(String cpf);
}
