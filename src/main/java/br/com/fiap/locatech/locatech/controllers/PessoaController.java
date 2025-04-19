package br.com.fiap.locatech.locatech.controllers;

import br.com.fiap.locatech.locatech.dtos.ResourceNotFoundDTO;
import br.com.fiap.locatech.locatech.entities.Pessoa;
import br.com.fiap.locatech.locatech.services.PessoaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pessoas")
@Tag(name = "Pessoa", description = "Controller para CRUD de pessoas")
public class PessoaController {

    private final Logger logger = LoggerFactory.getLogger(PessoaController.class);
    private final PessoaService pessoaService;

    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    // http://localhost:8080/pessoas?page=1&size=10

    @Operation(
            description = "Busca todas as pessoas paginadas",
            summary = "Busca de pessoas",
            responses = {
                    @ApiResponse(
                            description = "Ok",
                            responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = Pessoa.class)
                                    )
                            }
                    )
            }
    )
    @GetMapping
    public ResponseEntity<List<Pessoa>> findAllPessoas(
            @RequestParam("page") int page,
            @RequestParam("size") int size
    )
    {
        logger.info("/pessoas");
        var pessoas = this.pessoaService.findAllPessoas(page, size);
        return ResponseEntity.ok(pessoas);
    }

    // http://localhost:8080/pessoas/1

    @Operation(
            description = "Busca uma pessoa baseado no id especificado",
            summary = "Busca de uma pessoa específica",
            responses = {
                    @ApiResponse(
                            description = "Ok",
                            responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = Pessoa.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            description = "Not found",
                            responseCode = "404",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ResourceNotFoundDTO.class)
                                    )
                            }
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Pessoa>> findPessoaById(
            @PathVariable("id") Long id
    )
    {
        logger.info("/pessoas/" + id);
        var pessoa = this.pessoaService.findById(id);
        return ResponseEntity.ok(pessoa);
    }

    // POST -> http://localhost:8080/pessoas

    @Operation(
            description = "Salva um pessoa com base nos dados do corpo da requisição",
            summary = "Salva um pessoa",
            responses = {
                    @ApiResponse(description = "Created", responseCode = "201")
            }
    )
    @PostMapping
    public ResponseEntity<Void> savePessoa(
            @RequestBody Pessoa pessoa
    )
    {
        logger.info("POST -> /pessoas");
        this.pessoaService.savePessoa(pessoa);
        return ResponseEntity.status(201).build();
    }

    // PUT -> http://localhost:8080/pessoas/1

    @Operation(
            description = "Atualiza uma pessoa baseada no id especificada e com as informações do corpo da requisição",
            summary = "Atualiza uma pessoa específica",
            responses = {
                    @ApiResponse(description = "Ok", responseCode = "200"),
                    @ApiResponse(
                            description = "Not found",
                            responseCode = "404",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ResourceNotFoundDTO.class)
                                    )
                            }
                    )
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePessoa(
            @PathVariable("id") Long id,
            @RequestBody Pessoa pessoa
    )
    {
        logger.info("PUT -> /pessoas/" + id);
        this.pessoaService.updatePessoa(pessoa, id);
        return ResponseEntity.ok().build();
    }

    // DELETE -> http://localhost:8080/pessoas/1

    @Operation(
            description = "Deleta uma pessoa baseada no id especificado",
            summary = "Deleta uma pessoa específica",
            responses = {
                    @ApiResponse(description = "Ok", responseCode = "200"),
                    @ApiResponse(
                            description = "Not found",
                            responseCode = "404",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ResourceNotFoundDTO.class)
                                    )
                            }
                    )
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePessoa(
            @PathVariable("id") Long id
    )
    {
        logger.info("DELETE -> /pessoas/" + id);
        this.pessoaService.deletePessoa(id);
        return ResponseEntity.ok().build();
    }
}