#language: pt
Funcionalidade: Gerenciamento de Clientes

  Cenario: Registrar um novo cliente
    Dado que eu tenho dados do cliente com nome "João Silva", email "joao.silva@example.com", e cpf "12345678901"
    Quando eu registrar o cliente
    Entao o cliente deve ser registrado com sucesso
