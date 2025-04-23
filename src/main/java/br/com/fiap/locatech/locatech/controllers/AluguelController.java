package br.com.fiap.locatech.locatech.controllers;

import br.com.fiap.locatech.locatech.dtos.AluguelRequestDTO;
import br.com.fiap.locatech.locatech.dtos.ResourceNotFoundDTO;
import br.com.fiap.locatech.locatech.entities.Aluguel;
import br.com.fiap.locatech.locatech.services.AluguelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/alugueis")
@Tag(name = "Alugel", description = "Controller para CRUD de alugueis")
public class AluguelController {

    private final Logger logger = LoggerFactory.getLogger(AluguelController.class);
    private final AluguelService aluguelService;

    public AluguelController(AluguelService aluguelService) {
        this.aluguelService = aluguelService;
    }

    // http://localhost:8080/alugueis?page=1&size=10

    @Operation(
            description = "Busca todos os alugueis paginados",
            summary = "Busca de alugueis",
            responses = {
                    @ApiResponse(
                            description = "Ok",
                            responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = Aluguel.class)
                                    )
                            }
                    )
            }
    )
    @GetMapping
    public ResponseEntity<List<Aluguel>> findAllAlugueis(
            @RequestParam("page") int page,
            @RequestParam("size") int size
    )
    {
        logger.info("/alugueis");
        var alugueis = this.aluguelService.findAllAlugueis(page, size);
        return ResponseEntity.ok(alugueis);
    }

    // http://localhost:8080/alugueis/1

    @Operation(
            description = "Busca um aluguel baseado no id especificado",
            summary = "Busca de um aluguel específico",
            responses = {
                    @ApiResponse(
                            description = "Ok",
                            responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = Aluguel.class)
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
    public ResponseEntity<Optional<Aluguel>> findAluguelById(
            @PathVariable Long id
    )
    {
        logger.info("/alugueis/" + id);
        var aluguel = this.aluguelService.findById(id);
        return ResponseEntity.ok(aluguel);
    }

    // POST -> http://localhost:8080/alugueis

    @Operation(
            description = "Salva um aluguel com base nos dados do corpo da requisição",
            summary = "Salva um aluguel",
            responses = {
                    @ApiResponse(description = "Created", responseCode = "201")
            }
    )
    @PostMapping
    public ResponseEntity<Void> saveAluguel(
            @Valid @RequestBody AluguelRequestDTO aluguel
    )
    {
        logger.info("POST -> /alugueis");
        this.aluguelService.saveAluguel(aluguel);
        return ResponseEntity.status(201).build();
    }

    // PUT -> http://localhost:8080/alugueis/1

    @Operation(
            description = "Atualiza um aluguel baseado no id especificado e com as informações do corpo da requisição",
            summary = "Atualiza um aluguel específico",
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
    public ResponseEntity<Void> updateAluguel(
            @PathVariable Long id,
            @RequestBody Aluguel aluguel
    )
    {
        logger.info("PUT -> /alugueis/" + id);
        this.aluguelService.updateAluguel(aluguel, id);
        return ResponseEntity.ok().build();
    }

    // DELETE -> http://localhost:8080/alugueis/1

    @Operation(
            description = "Deleta um aluguel baseado no id especificado",
            summary = "Deleta um aluguel específico",
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
    public ResponseEntity<Void> deleteAluguel(
            @PathVariable Long id
    )
    {
        logger.info("DELETE -> /alugueis/" + id);
        this.aluguelService.deleteAluguel(id);
        return ResponseEntity.ok().build();
    }
}