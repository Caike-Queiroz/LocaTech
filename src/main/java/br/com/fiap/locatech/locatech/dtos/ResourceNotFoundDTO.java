package br.com.fiap.locatech.locatech.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

public record ResourceNotFoundDTO(
        @Schema(description = "mensagem de erro")
        String message,
        @Schema(description = "status code que acompanhará a mensgem")
        int status
) {}