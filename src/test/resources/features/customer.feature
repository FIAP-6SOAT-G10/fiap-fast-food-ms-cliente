#language: pt

Funcionalidade: Gerenciamento de Clientes
  Cenario: Buscar cliente
    Dado que recebo um identificador de cliente valido
    Quando realizar a busca
    E o cliente nao existir
    Entao os detalhes do cliente nao devem ser retornados
    E o codigo 404 deve ser apresentado
