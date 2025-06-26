package com.forumhub.api.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.forumhub.api.model.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String gerarToken(Usuario usuario) {
        return JWT.create()
                .withIssuer("ForumHub")
                .withSubject(usuario.getEmail())
                .withExpiresAt(Date.from(Instant.now().plus(2, ChronoUnit.HOURS)))
                .sign(Algorithm.HMAC256(secret));
    }
}
