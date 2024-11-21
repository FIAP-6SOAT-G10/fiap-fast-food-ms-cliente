//package br.com.fiap.techchallenge.apis.cliente;
//
//import br.com.fiap.techchallenge.naousar.apis.CustomerController;
//import br.com.fiap.techchallenge.infra.persistence.entities.Customer;
//import br.com.fiap.techchallenge.infra.mapper.cliente.CustomerMapper;
//import br.com.fiap.techchallenge.naousar.domain.valueobjects.CustomerDTO;
//import br.com.fiap.techchallenge.naousar.infra.exception.BaseException;
//import br.com.fiap.techchallenge.naousar.infra.exception.CustomerException;
//import br.com.fiap.techchallenge.infra.persistence.CustomerRepository;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.Pageable;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.when;
//
//@SpringBootTest
//class ClienteControllerTest {
//
//    @Mock
//    private CustomerRepository clienteRepository;
//
//    @Autowired
//    private CustomerMapper clienteMapper;
//
//    @Test
//    void shouldCadastrarClienteComSucesso() throws BaseException {
//        when(clienteRepository.findByCpf(anyString())).thenReturn(Optional.empty());
//        when(clienteRepository.saveAndFlush(any())).thenReturn(new Customer());
//        CustomerDTO clienteRequest = CustomerDTO
//                .builder()
//                .nome("John Doo")
//                .email("email@email")
//                .cpf("00000000001")
//                .build();
//
//        CustomerController clienteController = new CustomerController(clienteRepository, clienteMapper);
//        assertEquals(201, clienteController.cadastrar(clienteRequest).getStatusCode().value());
//    }
//
//    @Test
//    void mustLancarClienteExceptionAoCadastrarClienteComCPFQueJaExiste() throws BaseException {
//        when(clienteRepository.findByCpf("00000000000")).thenReturn(criarClienteOptional());
//        CustomerDTO clienteRequest = CustomerDTO
//                .builder()
//                .nome("John Doo")
//                .email("email@email")
//                .cpf("00000000000")
//                .build();
//
//        CustomerController clienteController = new CustomerController(clienteRepository, clienteMapper);
//        assertThrows(CustomerException.class, () -> clienteController.cadastrar(clienteRequest));
//    }
//
//    @Test
//    void mustLancarClienteExceptionAoCadastrarClienteComNomeVazio() throws BaseException {
//        CustomerDTO clienteRequest = CustomerDTO
//                .builder()
//                .email("email@email")
//                .cpf("00000000000")
//                .build();
//
//        CustomerController clienteController = new CustomerController(clienteRepository, clienteMapper);
//        assertThrows(CustomerException.class, () -> clienteController.cadastrar(clienteRequest));
//    }
//
//    @Test
//    void mustLancarClienteExceptionAoCadastrarClienteComEmailVazio() throws BaseException {
//        CustomerDTO clienteRequest = CustomerDTO
//                .builder()
//                .nome("John Doo")
//                .cpf("00000000000")
//                .build();
//
//        CustomerController clienteController = new CustomerController(clienteRepository, clienteMapper);
//        assertThrows(CustomerException.class, () -> clienteController.cadastrar(clienteRequest));
//    }
//
//    @Test
//    void mustLancarClienteExceptionAoCadastrarClienteComCPFVazio() throws BaseException {
//        CustomerDTO clienteRequest = CustomerDTO
//                .builder()
//                .nome("John Doo")
//                .email("email@email")
//                .build();
//
//        CustomerController clienteController = new CustomerController(clienteRepository, clienteMapper);
//        assertThrows(CustomerException.class, () -> clienteController.cadastrar(clienteRequest));
//    }
//
//    private CustomerDTO criarClienteRetorno() {
//        return CustomerDTO
//                .builder()
//                .nome("John Doo")
//                .email("email@email")
//                .cpf("00000000000")
//                .build();
//    }
//
//    private Optional<Customer> criarClienteOptional() {
//        return Optional.of(Customer
//                .builder()
//                .nome("John Doo")
//                .email("email@email")
//                .cpf("00000000000")
//                .build());
//    }
//
//    @Test
//    void shouldReturnNoContentWhenListarTodosClientesAndNoClientesExist() {
//        when(clienteRepository.findAll(Pageable.ofSize(10))).thenReturn(Page.empty());
//
//        CustomerController clienteController = new CustomerController(clienteRepository, clienteMapper);
//        ResponseEntity<List<CustomerDTO>> response = clienteController.listarTodosClientes(0, 10, null, null);
//        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
//    }
//
//    @Test
//    void shouldReturnOkWhenListarTodosClientesAndClientesExist() {
//        when(clienteRepository.findAll(Pageable.ofSize(10))).thenReturn(new PageImpl<>(List.of(new Customer())));
//
//        CustomerController clienteController = new CustomerController(clienteRepository, clienteMapper);
//        ResponseEntity<List<CustomerDTO>> response = clienteController.listarTodosClientes(0, 10, null, null);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//    }
//
//
//}
