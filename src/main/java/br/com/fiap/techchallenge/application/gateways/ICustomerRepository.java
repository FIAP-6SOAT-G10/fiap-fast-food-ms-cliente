package br.com.fiap.techchallenge.application.gateways;

import br.com.fiap.techchallenge.domain.entities.customer.Customer;
import com.github.fge.jsonpatch.JsonPatch;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ICustomerRepository {

    Customer updateCustomerData(Long id, Customer customer);

    Customer updateCustomer(Long id, Customer customer);

    Customer saveCustomer(Customer customer);

    List<Customer> listCustomers(String email, String cpf);

    Optional<Customer> findByCpf(String cpf);

    Optional<Customer> findById(Long id);
}
