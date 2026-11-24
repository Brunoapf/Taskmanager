package com.bruno.taskmanager.domain.tarefa;

import jakarta.validation.constraints.NotBlank;

public record DadosAtualizacaoTarefa(
        @NotBlank
        String titulo,

        String descricao,

        boolean concluida
) {
}
