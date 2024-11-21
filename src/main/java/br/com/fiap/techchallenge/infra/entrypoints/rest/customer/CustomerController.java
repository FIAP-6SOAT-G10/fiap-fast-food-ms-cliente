package br.com.fiap.techchallenge.infra.entrypoints.rest.customer;

import br.com.fiap.techchallenge.application.usecases.cliente.UpdateParcialCustomerUseCase;
import br.com.fiap.techchallenge.application.usecases.cliente.UpdateCustomerUseCase;
import br.com.fiap.techchallenge.application.usecases.cliente.RegisterCustomerUseCase;
import br.com.fiap.techchallenge.application.usecases.cliente.ListCustomerUseCase;
import br.com.fiap.techchallenge.domain.ErrorsResponse;
import br.com.fiap.techchallenge.domain.entities.customer.Customer;
import br.com.fiap.techchallenge.infra.entrypoints.rest.customer.model.CustomerDTO;
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

@Slf4j
@RestController
@Tag(name = "Clientes", description = "Conjunto de operações que podem ser realizadas no contexto de clientes.")
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final RegisterCustomerUseCase registerCustomerUseCase;
    private final ListCustomerUseCase listCustomerUseCase;
    private final UpdateParcialCustomerUseCase updateParcialCustomerUseCase;
    private final UpdateCustomerUseCase updateCustomerUseCase;

    @Operation(summary = "Cadastrar Customer", description = "Esta operação consiste em criar um novo cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content =
                    {@Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content =
                    {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorsResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorsResponse.class))})})
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<Void> registerCustomer(@RequestBody CustomerDTO clienteRequest) {
        log.info("cadastrar um cliente");
        Customer customer = registerCustomerUseCase.saveCustomer(new Customer(clienteRequest.getCpf(), clienteRequest.getName(), clienteRequest.getEmail()));
        if (customer == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
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
                                                              @RequestParam(required = false) String cpf
    ) {
        List<Customer> customers = listCustomerUseCase.listCustomers(email, cpf);
        if (customers.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(customers.stream().map(customer -> new CustomerDTO(customer.getId(), customer.getCpf(), customer.getName(), customer.getEmail())).toList());
    }

    @Operation(summary = "Atualizar Dados do Customer", description = "Está operação consiste em atualizar os dados do cliente cadastrado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Updated", content = {
                    @Content(mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorsResponse.class))
            }),
            @ApiResponse(responseCode = "404", description = "Not Found", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorsResponse.class))
            }
            ),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorsResponse.class))
            })
    })
    @PatchMapping(path = "/{id}", consumes = "application/json", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<CustomerDTO> updateCustomerData(@PathVariable("id") String id, @RequestBody JsonPatch patch) {
        log.info("Atualizando customer.");
        Customer customer = updateParcialCustomerUseCase.updateCustomerData(id, patch);
        return ResponseEntity.ok(new CustomerDTO(customer.getId(), customer.getCpf(), customer.getName(), customer.getEmail()));
    }

    @Operation(summary = "Atualizar Customer", description = "Está operação consiste em atualizar o cliente cadastrado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Updated", content = {
                    @Content(mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorsResponse.class))
            }),
            @ApiResponse(responseCode = "404", description = "Not Found", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorsResponse.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorsResponse.class))
            })
    })
    @PutMapping(path = "/{id}", consumes = "application/json")
    @CrossOrigin(origins = "*", maxAge = 3600)
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable("id") String id, @RequestBody CustomerDTO clienteRequest
    ) {
        log.info("Atualizando customer.");
        Customer customer = updateCustomerUseCase.updateCustomers(id, new Customer(clienteRequest.getCpf(), clienteRequest.getName(), clienteRequest.getEmail()));
        if (customer == null) {
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

}
