package br.com.fiap.techchallenge.application.usecases.costumers;

import br.com.fiap.techchallenge.application.gateways.ICustomerRepository;
import br.com.fiap.techchallenge.domain.ErrorsEnum;
import br.com.fiap.techchallenge.domain.entities.customer.Customer;
import br.com.fiap.techchallenge.infra.exception.CustomerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
public class FindCustomerByCpfUseCase {

    private final ICustomerRepository customerRepository;

    public FindCustomerByCpfUseCase(ICustomerRepository clienteRepository) {
        this.customerRepository = clienteRepository;
    }

    public Optional<Customer> findByCpf(String cpf) {
      Optional<Customer> customerOptional =  customerRepository.findByCpf(cpf);
        if (customerOptional.isPresent()) {
            log.info("Cliente encontrado: {}", customerOptional.get().getId());
        } else {
            log.info("Cliente não encontrado para o CPF: {}", cpf);
        }
        if (customerOptional.isEmpty()) {
            throw new CustomerException(ErrorsEnum.CLIENTE_CPF_NAO_EXISTE);
        }
      return customerOptional;
    }

    public Optional<Customer> findByID(Long id) {
      Optional<Customer> customerOptional =  customerRepository.findById(id);
        if (customerOptional.isPresent()) {
            log.info("Cliente encontrado: {}", customerOptional.get().getId());
        } else {
            log.info("Cliente não encontrado para o ID: {}", id);
        }
        if (customerOptional.isEmpty()) {
            throw new CustomerException(ErrorsEnum.CLIENTE_NAO_ENCONTRADO);
        }
      return customerOptional;
    }
}
