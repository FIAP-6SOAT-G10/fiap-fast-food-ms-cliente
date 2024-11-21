package br.com.fiap.techchallenge.domain.entities.customer;

public class Customer {
    private Long id;
    private String cpf;
    private String name;
    private String email;

    public Customer() {
    }

    public Customer(String cpf) {
        if (cpf == null || cpf.isEmpty() || cpf.isBlank()) {
            throw new IllegalArgumentException("Cpf é um campo obrigatório no cadastro de novos clientes.");
        }
        this.cpf = cpf;
    }

    public Customer(Long id, String cpf, String name, String email) {
        this(cpf, name, email);
        this.id = id;
    }

    public Customer(String cpf, String name, String email) {

        if (cpf == null || cpf.isEmpty() || cpf.isBlank()) {
            throw new IllegalArgumentException("Cpf é um campo obrigatório no cadastro de novos clientes.");
        }

        if (name == null || name.isEmpty() || name.isBlank()) {
            throw new IllegalArgumentException("Nome é um campo obrigatório no cadastro de novos clientes.");
        }

        if (email == null || email.isEmpty() || email.isBlank()) {
            throw new IllegalArgumentException("E-mail é um campo obrigatório no cadastro de novos clientes.");
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

