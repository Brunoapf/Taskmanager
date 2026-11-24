package com.bruno.taskmanager.controller;

import com.bruno.taskmanager.domain.usuario.DadosCadastro;
import com.bruno.taskmanager.domain.usuario.Usuario;
import com.bruno.taskmanager.domain.usuario.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/cadastros")
public class CadastroController {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    public CadastroController(
            UsuarioRepository repository,
            PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
    public ResponseEntity<Void> cadastrar(
            @RequestBody @Valid DadosCadastro dados) {

        if (repository.existsByLogin(dados.login())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Este login já está cadastrado"
            );
        }

        String senhaCriptografada =
                passwordEncoder.encode(dados.senha());

        Usuario usuario = new Usuario(
                dados.login(),
                senhaCriptografada
        );

        repository.save(usuario);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
