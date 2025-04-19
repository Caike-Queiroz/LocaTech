package br.com.fiap.locatech.locatech.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record AluguelRequestDTO(

        @Schema(description = "Id da pessoa que está alugando o veículo")
        @NotNull(message = "O id da pessoa não pode ser nulo")
        Long pessoaId,
        @Schema(description = "Id do veículo que está sendo alugado")
        @NotNull(message = "O id do veículo não pode ser nulo")
        Long veiculoId,
        @Schema(description = "Data de início do aluguel do carro")
        LocalDate dataInicio,
        @Schema(description = "Data fim do aluguel do carro")
        LocalDate dataFim
) {}