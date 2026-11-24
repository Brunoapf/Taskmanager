package com.bruno.taskmanager.controller;

import com.bruno.taskmanager.domain.tarefa.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import com.bruno.taskmanager.domain.usuario.Usuario;
import com.bruno.taskmanager.domain.usuario.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@SecurityRequirement(name = "bearer-key")
@RestController
@RequestMapping("/tarefas")
public class TarefaController {

    private final TarefaRepository tarefaRepository;
    private final UsuarioRepository usuarioRepository;

    public TarefaController(
            TarefaRepository tarefaRepository,
            UsuarioRepository usuarioRepository) {
        this.tarefaRepository = tarefaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DadosRespostaTarefa criar(
            @RequestBody @Valid DadosTarefa dados,
            @AuthenticationPrincipal Usuario usuario) {

        Tarefa tarefa = new Tarefa(
                dados.titulo(),
                dados.descricao(),
                usuario
        );

        tarefaRepository.save(tarefa);

        return new DadosRespostaTarefa(tarefa);
    }

    @GetMapping
    public List<DadosRespostaTarefa> listar(
            @AuthenticationPrincipal Usuario usuario) {

        return tarefaRepository.findAllByUsuarioId(usuario.getId())
                .stream()
                .map(DadosRespostaTarefa::new)
                .toList();
    }

    @PutMapping("/{id}")
    public DadosRespostaTarefa atualizar(
            @PathVariable Long id,
            @RequestBody @Valid DadosAtualizacaoTarefa dados,
            @AuthenticationPrincipal Usuario usuario) {

        Tarefa tarefa = buscarTarefa(id, usuario);

        tarefa.setTitulo(dados.titulo());
        tarefa.setDescricao(dados.descricao());
        tarefa.setConcluida(dados.concluida());

        tarefaRepository.save(tarefa);

        return new DadosRespostaTarefa(tarefa);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(
            @PathVariable Long id,
            @AuthenticationPrincipal Usuario usuario) {

        Tarefa tarefa = buscarTarefa(id, usuario);
        tarefaRepository.delete(tarefa);
    }

    private Tarefa buscarTarefa(Long id, Usuario usuario) {
        return tarefaRepository.findByIdAndUsuarioId(
                id,
                usuario.getId()
        ).orElseThrow(() ->
                new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Tarefa não encontrada"
                )
        );
    }
}
