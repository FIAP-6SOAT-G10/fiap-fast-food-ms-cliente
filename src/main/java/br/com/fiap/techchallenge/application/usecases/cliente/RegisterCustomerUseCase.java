package br.com.fiap.techchallenge.application.usecases.cliente;

import br.com.fiap.techchallenge.application.gateways.ICustomerRepository;
import br.com.fiap.techchallenge.domain.ErrorsEnum;
import br.com.fiap.techchallenge.domain.entities.customer.Customer;
import br.com.fiap.techchallenge.infra.exception.BaseException;
import br.com.fiap.techchallenge.infra.exception.CustomerException;

import java.util.Optional;

public class RegisterCustomerUseCase {

    private final ICustomerRepository clienteRepository;

    public RegisterCustomerUseCase(ICustomerRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Customer saveCustomer(Customer customer) {
        validateData(customer);
        return this.clienteRepository.saveCustomer(customer);
    }

    private void validateData(Customer customer) throws BaseException {
        if (valueIsNullOrEmpty(customer.getName())) {
            throw new CustomerException(ErrorsEnum.CLIENTE_NOME_OBRIGATORIO);
        }

        if (valueIsNullOrEmpty(customer.getEmail())) {
            throw new CustomerException(ErrorsEnum.CLIENTE_EMAIL_OBRIGATORIO);
        }

        if (valueIsNullOrEmpty(customer.getCpf())) {
            throw new CustomerException(ErrorsEnum.CLIENTE_CPF_INVALIDO);
        }

        validateCpfRegistered(customer.getCpf());
    }

    public static boolean valueIsNullOrEmpty(String value) {
        return (value == null || value.isEmpty());
    }

    public void validateCpfRegistered(String cpf) {
        Optional<Customer> cliente = clienteRepository.findByCpf(cpf);
        if (cliente.isPresent()) {
            throw new CustomerException(ErrorsEnum.CLIENTE_JA_CADASTRADO);
        }
    }

}
