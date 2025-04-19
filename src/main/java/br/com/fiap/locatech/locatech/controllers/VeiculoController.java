package br.com.fiap.locatech.locatech.controllers;

import br.com.fiap.locatech.locatech.dtos.ResourceNotFoundDTO;
import br.com.fiap.locatech.locatech.entities.Veiculo;
import br.com.fiap.locatech.locatech.services.VeiculoService;
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
@RequestMapping("/veiculos")
@Tag(name = "Veículo", description = "Controller para CRUD de veículos")
public class VeiculoController {

    private static final Logger logger = LoggerFactory.getLogger(VeiculoController.class);
    private final VeiculoService veiculoService;

    public VeiculoController(VeiculoService veiculoService) {
        this.veiculoService = veiculoService;
    }

    // http://localhost:8080/veiculos?page=1&size=10

    @Operation(
            description = "Busca todos os veículos paginados",
            summary = "Busca de veículos",
            responses = {
                    @ApiResponse(
                            description = "Ok",
                            responseCode = "200",
                            content = {
                                @Content(
                                        mediaType = "application/json",
                                        schema = @Schema(implementation = Veiculo.class)
                                )
                            }
                    )
            }
    )
    @GetMapping
    public ResponseEntity<List<Veiculo>> findAllVeiculos(
            @RequestParam("page") int page,
            @RequestParam("size") int size
    )
    {
        logger.info("/veiculos");
        var veiculos = this.veiculoService.findAllVeiculos(page, size);
        return ResponseEntity.ok(veiculos);
    }

    // http://localhost:8080/veiculos/1

    @Operation(
            description = "Busca um veículo baseado no id especificado",
            summary = "Busca de um veículo específico",
            responses = {
                    @ApiResponse(
                            description = "Ok",
                            responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = Veiculo.class)
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
    public ResponseEntity<Optional<Veiculo>> findVeiculoById(
            @PathVariable("id") Long id
    )
    {
        logger.info("/veiculos/" + id);
        var veiculo = this.veiculoService.findVeiculoById(id);
        return ResponseEntity.ok(veiculo);
    }

    // POST -> http://localhost:8080/veiculos

    @Operation(
            description = "Salva um veículo com base nos dados do corpo da requisição",
            summary = "Salva um veículo",
            responses = {
                    @ApiResponse(description = "Created", responseCode = "201")
            }
    )
    @PostMapping
    public ResponseEntity<Void> saveVeiculo(
            @RequestBody Veiculo veiculo
    )
    {
        logger.info("POST -> /veiculos");
        this.veiculoService.saveVeiculo(veiculo);
        return ResponseEntity.status(201).build();
    }

    // PUT -> http://localhost:8080/veiculos/1

    @Operation(
            description = "Atualiza um veículo baseado no id especificado e com as informações do corpo da requisição",
            summary = "Atualiza um veículo específico",
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
    public ResponseEntity<Void> updateVeiculo(
            @PathVariable("id") Long id,
            @RequestBody Veiculo veiculo
    )
    {
        logger.info("PUT -> /veiculos/" + id);
        this.veiculoService.updateVeiculo(veiculo, id);
        return ResponseEntity.ok().build();
    }

    // DELETE -> http://localhost:8080/veiculos/1

    @Operation(
            description = "Deleta um veículo baseado no id especificado",
            summary = "Deleta um veículo específico",
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
    public ResponseEntity<Void> deleteVeiculo(
            @PathVariable("id") Long id
    )
    {
        logger.info("DELETE -> /veiculos/" + id);
        this.veiculoService.deleteVeiculo(id);
        return ResponseEntity.ok().build();
    }
}