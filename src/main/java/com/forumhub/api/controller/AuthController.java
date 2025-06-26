package com.forumhub.api.controller;

import com.forumhub.api.dto.LoginRequest;
import com.forumhub.api.dto.TokenResponse;
import com.forumhub.api.model.Usuario;
import com.forumhub.api.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getSenha()
                    )
            );

            Usuario usuario = (Usuario) authentication.getPrincipal();
            String token = tokenService.gerarToken(usuario);
            return ResponseEntity.ok(new TokenResponse(token));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Credenciais inválidas!");
        }
    }

    // Endpoint público para gerar senha (usado para testes)
    @GetMapping("/gerar-senha")
    public ResponseEntity<String> gerarSenha(@RequestParam String senha) {
        String senhaCriptografada = new BCryptPasswordEncoder().encode(senha);
        return ResponseEntity.ok(senhaCriptografada);
    }
}
