package br.com.fiap.techchallenge.infra.gateways;

import br.com.fiap.techchallenge.application.gateways.ICustomerRepository;
import br.com.fiap.techchallenge.domain.ErrorsEnum;
import br.com.fiap.techchallenge.domain.entities.customer.Customer;
import br.com.fiap.techchallenge.infra.dataproviders.database.persistence.CustomerEntityRepository;
import br.com.fiap.techchallenge.infra.dataproviders.database.persistence.entities.CustomerEntity;
import br.com.fiap.techchallenge.infra.exception.CustomerAlreadyExistsException;
import br.com.fiap.techchallenge.infra.exception.CustomerException;
import br.com.fiap.techchallenge.infra.mapper.CustomerMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Slf4j
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
        log.info("Atualizando dados do cliente com ID: {}", id);
        try {
            Optional<CustomerEntity> optionalCustomerEntity = customerEntityRepository.findById(id);
            if (!optionalCustomerEntity.isPresent()) {
                log.error("Cliente não encontrado para o ID: {}", id);
                throw new CustomerException(ErrorsEnum.CLIENTE_CPF_NAO_EXISTENTE);
            }

            CustomerEntity customerEntity = optionalCustomerEntity.get();
            JsonNode customerNode = objectMapper.convertValue(customerEntity, JsonNode.class);
            JsonNode patched = patch.apply(customerNode);

            if (patched == null) {
                log.error("Erro ao aplicar patch no cliente com ID: {}", id);
                throw new CustomerException(ErrorsEnum.CLIENTE_FALHA_GENERICA, "Patched node is null");
            }

            CustomerEntity updatedCustomerEntity = objectMapper.treeToValue(patched, CustomerEntity.class);
            updatedCustomerEntity = customerEntityRepository.saveAndFlush(updatedCustomerEntity);
            log.info("Dados do cliente atualizados com sucesso: {}", updatedCustomerEntity.getId());

            return customerMapper.fromEntityToDomain(updatedCustomerEntity);
        } catch (JsonPatchException e) {
            log.error("Erro ao aplicar patch no cliente com ID: {}: {}", id, e.getMessage());
            throw new CustomerException(ErrorsEnum.CLIENTE_FALHA_DURANTE_ATUALIZACAO, String.valueOf(e));
        } catch (Exception e) {
            log.error("Erro genérico ao atualizar cliente com ID: {}: {}", id, e.getMessage());
            throw new CustomerException(ErrorsEnum.CLIENTE_FALHA_GENERICA, String.valueOf(e));
        }
}

    @Override
    public Customer updateCustomer(Long id, Customer customer) {
        log.info("Atualizando cliente com ID: {}", id);
        CustomerEntity novoCustomerEntity = customerMapper.fromDomainToEntity(customer);

        Optional<CustomerEntity> clienteOptional = customerEntityRepository.findById(id);

        if (clienteOptional.isPresent()) {
            CustomerEntity antigoCustomerEntity = clienteOptional.get();
            fillWithNewData(antigoCustomerEntity, novoCustomerEntity);
            log.info("Cliente atualizado com sucesso: {}", antigoCustomerEntity.getId());
            return customerMapper.fromEntityToDomain(customerEntityRepository.saveAndFlush(antigoCustomerEntity));
        } else {
            log.error("Cliente não encontrado para o ID: {}", id);
            throw new CustomerException(ErrorsEnum.CLIENTE_CODIGO_IDENTIFICADOR_INVALIDO);
        }
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        log.info("Salvando novo cliente com CPF: {}", customer.getCpf());
        if (customerEntityRepository.findByCpf(customer.getCpf()).isPresent()) {
            log.error("Cliente já cadastrado com CPF: {}", customer.getCpf());
            throw new CustomerAlreadyExistsException(ErrorsEnum.CLIENTE_JA_CADASTRADO);
        }
        Customer savedCustomer = customerMapper.fromEntityToDomain(customerEntityRepository.saveAndFlush(customerMapper.fromDomainToEntity(customer)));
        log.info("Cliente salvo com sucesso: {}", savedCustomer.getId());
        return savedCustomer;
    }

    @Override
    public List<Customer> listCustomers(String email, String cpf) {
        log.info("Listando clientes com email: {} e CPF: {}", email, cpf);
        List<CustomerEntity> listaCustomerEntity = new ArrayList<>();
        Predicate<CustomerEntity> predicate = customerEntity -> {
            Boolean hasSameEmail = email == null || customerEntity.getEmail().equals(email);
            Boolean hasSameCpf = cpf == null || customerEntity.getCpf().equals(cpf);
            return hasSameEmail && hasSameCpf;
        };

        if (email != null || cpf != null) {
            customerEntityRepository.findByEmailOrCpf(email, cpf).ifPresent(clienteList -> {
                List<CustomerEntity> filteredCustomersEntity = clienteList.stream().filter(predicate).toList();
                listaCustomerEntity.addAll(filteredCustomersEntity);
            });
        } else {
            listaCustomerEntity.addAll(customerEntityRepository.findAll());
        }

        if (listaCustomerEntity.isEmpty()) {
            log.error("Nenhum cliente encontrado com email: {} e CPF: {}", email, cpf);
            throw new CustomerException(ErrorsEnum.CLIENTE_NAO_ENCONTRADO);
        }

        log.info("Clientes encontrados: {}", listaCustomerEntity.size());
        return customerMapper.fromListEntityToListDomain(listaCustomerEntity);
    }

    @Override
    public Optional<Customer> findByCpf(String cpf) {
        Optional<Customer> customer = customerEntityRepository.findByCpf(cpf)
                .map(customerMapper::fromEntityToDomain);
        return customer;
    }

    private void fillWithNewData(CustomerEntity antigoCustomerEntity, CustomerEntity novoCustomerEntity) {
        antigoCustomerEntity.setCpf(novoCustomerEntity.getCpf());
        antigoCustomerEntity.setName(novoCustomerEntity.getName());
        antigoCustomerEntity.setEmail(novoCustomerEntity.getEmail());
    }
}
