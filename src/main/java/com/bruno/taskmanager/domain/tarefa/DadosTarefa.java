package com.bruno.taskmanager.domain.tarefa;

import jakarta.validation.constraints.NotBlank;

public record DadosTarefa(
        @NotBlank
        String titulo,

        String descricao
) {
}
