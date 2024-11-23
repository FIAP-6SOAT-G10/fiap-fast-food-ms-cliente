package br.com.fiap.techchallenge.domain.entities.customer;

import br.com.fiap.techchallenge.domain.ErrorsEnum;
import br.com.fiap.techchallenge.infra.exception.CustomerException;

public class Customer {
    private Long id;
    private String cpf;
    private String name;
    private String email;

    public Customer() {
    }

    public Customer(String cpf) {
        if (cpf == null || cpf.isEmpty() || cpf.isBlank()) {
            throw new CustomerException(ErrorsEnum.CLIENTE_CPF_OBRIGATORIO_CADASTRO.getMessage());
        }
        this.cpf = cpf;
    }

    public Customer(Long id, String cpf, String name, String email) {
        this(cpf, name, email);
        this.id = id;
    }

    public Customer(String cpf, String name, String email) {

        if (cpf == null || cpf.isEmpty() || cpf.isBlank()) {
            throw new CustomerException(ErrorsEnum.CLIENTE_CPF_OBRIGATORIO_CADASTRO.getMessage());
        }

        if (name == null || name.isEmpty() || name.isBlank()) {
            throw new CustomerException(ErrorsEnum.CLIENTE_NOME_OBRIGATORIO_CADASTRO.getMessage());
        }

        if (email == null || email.isEmpty() || email.isBlank()) {
            throw new CustomerException(ErrorsEnum.CLIENTE_EMAIL_OBRIGATORIO_CADASTRO.getMessage());
        }

        this.cpf = cpf;
        this.name = name;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getCpf() {
        return cpf;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}

