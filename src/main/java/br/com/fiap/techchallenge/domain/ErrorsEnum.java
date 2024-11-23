package br.com.fiap.techchallenge.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.Level;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorsEnum {

    /* ERROS GENÉRICOS 001 - 99 */
    ERRO_PARAMETROS("001","Parametro mandatorio não foi enviado", Level.ERROR, HttpStatus.BAD_REQUEST),

    /* ERROS DE CLIENTE 300 - 399 */
    CLIENTE_NAO_ENCONTRADO("205", "O identificador informado não está relacionado a nenhum cliente existente.", Level.ERROR, HttpStatus.NOT_FOUND),
    CLIENTE_CPF_INVALIDO("300", "CPF inválido.", Level.ERROR, HttpStatus.NOT_FOUND),
    CLIENTE_JA_CADASTRADO("301", "O cliente com o CPF informado já existe", Level.ERROR, HttpStatus.BAD_REQUEST),
    CLIENTE_EMAIL_OBRIGATORIO("302", "O email do cliente é obrigatório", Level.ERROR, HttpStatus.BAD_REQUEST),
    CLIENTE_CPF_OBRIGATORIO("303", "O CPF do cliente é obrigatório", Level.ERROR, HttpStatus.BAD_REQUEST),
    CLIENTE_CPF_NAO_EXISTENTE("304", "O campo cpf é obrigatório na atualização de um cliente.", Level.ERROR, HttpStatus.BAD_REQUEST),
    CLIENTE_NOME_OBRIGATORIO("305", "O nome do cliente é obrigatório", Level.ERROR, HttpStatus.BAD_REQUEST),
    CLIENTE_CPF_NAO_EXISTE("306", "CPF informado não existe", Level.ERROR, HttpStatus.BAD_REQUEST),
    CLIENTE_CODIGO_IDENTIFICADOR_INVALIDO("307", "O identificador do cliente é inválido", Level.ERROR, HttpStatus.BAD_REQUEST),
    CLIENTE_FALHA_DURANTE_ATUALIZACAO("308", "Erro durante a atualização do cliente no banco de dados.", Level.ERROR, HttpStatus.INTERNAL_SERVER_ERROR),
    CLIENTE_FALHA_GENERICA("309", "Erro genérico ao atualizar o cliente.", Level.ERROR, HttpStatus.INTERNAL_SERVER_ERROR),
    CLIENTE_EMAIL_OBRIGATORIO_CADASTRO("310", "E-mail é um campo obrigatório no cadastro de novos clientes.", Level.ERROR, HttpStatus.BAD_REQUEST),
    CLIENTE_NOME_OBRIGATORIO_CADASTRO("311", "Nome é um campo obrigatório no cadastro de novos clientes.", Level.ERROR, HttpStatus.BAD_REQUEST),
    CLIENTE_CPF_OBRIGATORIO_CADASTRO("312", "Cpf é um campo obrigatório no cadastro de novos clientes.", Level.ERROR, HttpStatus.BAD_REQUEST);

    private final String code;
    private final String message;
    private final Level logLevel;
    private final HttpStatus httpStatusCode;
}
