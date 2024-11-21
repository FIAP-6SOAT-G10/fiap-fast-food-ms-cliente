package br.com.fiap.techchallenge.infra.gateways;

import br.com.fiap.techchallenge.application.gateways.ICustomerRepository;
import br.com.fiap.techchallenge.domain.ErrorsEnum;
import br.com.fiap.techchallenge.domain.entities.customer.Customer;
import br.com.fiap.techchallenge.infra.dataproviders.database.persistence.CustomerEntityRepository;
import br.com.fiap.techchallenge.infra.dataproviders.database.persistence.entities.CustomerEntity;
import br.com.fiap.techchallenge.infra.exception.CustomerException;
import br.com.fiap.techchallenge.infra.mapper.CustomerMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class CustomerRepository implements ICustomerRepository {

    private final CustomerEntityRepository customerEntityRepository;
    private final CustomerMapper customerMapper;
    private final ObjectMapper objectMapper;

    public CustomerRepository(CustomerEntityRepository customerEntityRepository, CustomerMapper customerMapper, ObjectMapper objectMapper) {
        this.customerEntityRepository = customerEntityRepository;
        this.customerMapper = customerMapper;
        this.objectMapper = objectMapper;
    }

    @Override
    public Customer updateCustomerData(Long id, JsonPatch patch) {
        try {
            Optional<CustomerEntity> optionalCustomerEntity = customerEntityRepository.findById(id);
            if (!optionalCustomerEntity.isPresent()) {
                throw new CustomerException(ErrorsEnum.CLIENTE_CPF_NAO_EXISTENTE);
            }

            CustomerEntity customerEntity = optionalCustomerEntity.get();
            JsonNode customerNode = objectMapper.convertValue(customerEntity, JsonNode.class);
            JsonNode patched = patch.apply(customerNode);

            if (patched == null) {
                throw new CustomerException(ErrorsEnum.CLIENTE_FALHA_GENERICA, "Patched node is null");
            }

            CustomerEntity updatedCustomerEntity = objectMapper.treeToValue(patched, CustomerEntity.class);
            updatedCustomerEntity = customerEntityRepository.saveAndFlush(updatedCustomerEntity);

            return customerMapper.fromEntityToDomain(updatedCustomerEntity);
        } catch (JsonPatchException e) {
            throw new CustomerException(ErrorsEnum.CLIENTE_FALHA_DURANTE_ATUALIZACAO, String.valueOf(e));
        } catch (Exception e) {
            throw new CustomerException(ErrorsEnum.CLIENTE_FALHA_GENERICA, String.valueOf(e));
        }
}

    @Override
    public Customer updateCustomer(Long id, Customer customer) {
        CustomerEntity novoCustomerEntity = customerMapper.fromDomainToEntity(customer);

        Optional<CustomerEntity> clienteOptional = customerEntityRepository.findById(id);

        if (clienteOptional.isPresent()) {
            CustomerEntity antigoCustomerEntity = clienteOptional.get();
            fillWithNewData(antigoCustomerEntity, novoCustomerEntity);
            return customerMapper.fromEntityToDomain(customerEntityRepository.saveAndFlush(antigoCustomerEntity));
        } else {
            throw new CustomerException(ErrorsEnum.CLIENTE_CODIGO_IDENTIFICADOR_INVALIDO);
        }
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        return customerMapper.fromEntityToDomain(customerEntityRepository.saveAndFlush(customerMapper.fromDomainToEntity(customer)));
    }

    @Override
    public List<Customer> listCustomers(String email, String cpf) {
        List<CustomerEntity> listaCustomerEntity = new ArrayList<>();
        Predicate<CustomerEntity> predicate = cliente -> {
            Boolean hasSameEmail = email == null || cliente.getEmail().equals(email);
            Boolean hasSameCpf = cpf == null || cliente.getCpf().equals(cpf);
            return hasSameEmail && hasSameCpf;
        };

        if (email != null || cpf != null) {
            customerEntityRepository.findByEmailOrCpf(email, cpf).ifPresent(clienteList -> {
                List<CustomerEntity> filteredClientesEntity = clienteList.stream().filter(predicate).toList();
                listaCustomerEntity.addAll(filteredClientesEntity);
            });
        } else {
            listaCustomerEntity.addAll(customerEntityRepository.findAll());
        }

        return customerMapper.fromListEntityToListDomain(listaCustomerEntity);
    }

    @Override
    public Optional<Customer> findByCpf(String cpf) {
        return customerEntityRepository.findByCpf(cpf)
                .map(customerMapper::fromEntityToDomain);
    }

    private void fillWithNewData(CustomerEntity antigoCustomerEntity, CustomerEntity novoCustomerEntity) {
        antigoCustomerEntity.setCpf(novoCustomerEntity.getCpf());
        antigoCustomerEntity.setName(novoCustomerEntity.getName());
        antigoCustomerEntity.setEmail(novoCustomerEntity.getEmail());
    }
}
