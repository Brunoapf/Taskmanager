package com.bruno.taskmanager.controller;

import com.bruno.taskmanager.domain.usuario.DadosLogin;
import com.bruno.taskmanager.domain.usuario.DadosToken;
import com.bruno.taskmanager.domain.usuario.Usuario;
import com.bruno.taskmanager.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AutenticacaoController(
            AuthenticationManager authenticationManager,
            TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<DadosToken> login(
            @RequestBody @Valid DadosLogin dados) {

        var dadosAutenticacao =
                new UsernamePasswordAuthenticationToken(
                        dados.login(),
                        dados.senha()
                );

        var autenticacao =
                authenticationManager.authenticate(dadosAutenticacao);

        Usuario usuario = (Usuario) autenticacao.getPrincipal();

        String token = tokenService.gerarToken(usuario);

        return ResponseEntity.ok(new DadosToken(token));
    }
}
