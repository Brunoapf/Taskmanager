package com.bruno.taskmanager.domain.tarefa;

import com.bruno.taskmanager.domain.usuario.Usuario;
import jakarta.persistence.*;

@Entity
@Table(name = "tarefas")
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    private String descricao;

    private boolean concluida = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    public Tarefa() {
    }

    public Tarefa(String titulo, String descricao, Usuario usuario) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.usuario = usuario;
        this.concluida = false;
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isConcluida() {
        return concluida;
    }

    public void setConcluida(boolean concluida) {
        this.concluida = concluida;
    }

    public Usuario getUsuario() {
        return usuario;
    }
}
