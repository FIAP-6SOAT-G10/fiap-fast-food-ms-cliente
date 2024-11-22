package br.com.fiap.techchallenge.infra.entrypoints.rest.customer;

import br.com.fiap.techchallenge.application.usecases.cliente.*;
import br.com.fiap.techchallenge.domain.ErrorResponse;
import br.com.fiap.techchallenge.domain.entities.customer.Customer;
import br.com.fiap.techchallenge.infra.entrypoints.rest.customer.model.CustomerDTO;
import br.com.fiap.techchallenge.infra.exception.CustomerAlreadyExistsException;
import com.github.fge.jsonpatch.JsonPatch;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@Tag(name = "Clientes", description = "Conjunto de operações que podem ser realizadas no contexto de clientes.")
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final RegisterCustomerUseCase registerCustomerUseCase;
    private final ListCustomerUseCase listCustomerUseCase;
    private final FindCustomerByCpfUseCase findCustomerByIdUseCase;
    private final UpdateParcialCustomerUseCase updateParcialCustomerUseCase;
    private final UpdateCustomerUseCase updateCustomerUseCase;

    @Operation(summary = "Cadastrar Customer", description = "Esta operação consiste em criar um novo cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content =
                    {@Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content =
                    {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))})})
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<Void> registerCustomer(@RequestBody CustomerDTO clienteRequest) {
        log.info("Iniciando o cadastro de um cliente");
        try {
            Customer customer = registerCustomerUseCase.saveCustomer(new Customer(clienteRequest.getCpf(), clienteRequest.getName(), clienteRequest.getEmail()));
            log.info("Cliente cadastrado com sucesso: {}", customer.getId());
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (CustomerAlreadyExistsException e) {
            log.error("Erro ao cadastrar cliente: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Operation(summary = "Listar Clientes", description = "Está operação consiste em retornar todos os clientes cadastrados paginados por página e tamanho")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))
            }),
            @ApiResponse(responseCode = "204", description = "Not Found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))
            })
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<List<CustomerDTO>> listAllCustomers(@RequestParam(required = false) String email,
                                                              @RequestParam(required = false) String cpf) {
        log.info("Listando todos os clientes");
        List<Customer> customers = listCustomerUseCase.listCustomers(email, cpf);
        if (customers.isEmpty()) {
            log.info("Nenhum cliente encontrado");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        log.info("Clientes encontrados: {}", customers.size());
        return ResponseEntity.status(HttpStatus.OK).body(customers.stream().map(customer -> new CustomerDTO(customer.getId(), customer.getCpf(), customer.getName(), customer.getEmail())).toList());
    }

    @Operation(summary = "Atualizar Dados do Customer", description = "Está operação consiste em atualizar os dados do cliente cadastrado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Updated", content = {
                    @Content(mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            }),
            @ApiResponse(responseCode = "404", description = "Not Found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            }
            ),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            })
    })
    @PatchMapping(path = "/{id}", consumes = "application/json", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<CustomerDTO> updateCustomerData(@PathVariable("id") String id, @RequestBody JsonPatch patch) {
        log.info("Atualizando dados do cliente com ID: {}", id);
        try {
            Customer customer = updateParcialCustomerUseCase.updateCustomerData(id, patch);
            log.info("Dados do cliente atualizados com sucesso: {}", customer.getId());
            return ResponseEntity.ok(new CustomerDTO(customer.getId(), customer.getCpf(), customer.getName(), customer.getEmail()));
        } catch (Exception e) {
            log.error("Erro ao atualizar dados do cliente: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Atualizar Customer", description = "Está operação consiste em atualizar o cliente cadastrado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Updated", content = {
                    @Content(mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            }),
            @ApiResponse(responseCode = "404", description = "Not Found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            })
    })
    @PutMapping(path = "/{id}", consumes = "application/json")
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable("id") String id, @RequestBody CustomerDTO clienteRequest) {
        log.info("Atualizando cliente com ID: {}", id);
        try {
            Customer customer = updateCustomerUseCase.updateCustomers(id, new Customer(clienteRequest.getCpf(), clienteRequest.getName(), clienteRequest.getEmail()));
            if (customer == null) {
                log.info("Cliente não encontrado para o ID: {}", id);
                return ResponseEntity.notFound().build();
            }
            log.info("Cliente atualizado com sucesso: {}", customer.getId());
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Erro ao atualizar cliente: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Buscar Customer por CPF", description = "Esta operação consiste em buscar um cliente pelo CPF")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "Not Found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            })
    })
    @GetMapping(path = "/cpf/{cpf}", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<CustomerDTO> getCustomerByCpf(@PathVariable("cpf") String cpf) {
        log.info("Buscando cliente por CPF: {}", cpf);
        Optional<Customer> customer = findCustomerByIdUseCase.findByCpf(cpf);
        if (customer.isPresent()) {
            log.info("Cliente encontrado: {}", customer.get().getId());
            CustomerDTO customerDTO = new CustomerDTO(customer.get().getId(), customer.get().getCpf(), customer.get().getName(), customer.get().getEmail());
            return ResponseEntity.ok(customerDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
