package com.bruno.taskmanager.domain.tarefa;

public record DadosRespostaTarefa(
        Long id,
        String titulo,
        String descricao,
        boolean concluida
) {
    public DadosRespostaTarefa(Tarefa tarefa) {
        this(
                tarefa.getId(),
                tarefa.getTitulo(),
                tarefa.getDescricao(),
                tarefa.isConcluida()
        );
    }
}
