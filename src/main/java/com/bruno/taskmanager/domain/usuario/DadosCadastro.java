package com.bruno.taskmanager.domain.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DadosCadastro(
        @NotBlank
        String login,

        @NotBlank
        @Size(min = 8)
        String senha
) {
}
