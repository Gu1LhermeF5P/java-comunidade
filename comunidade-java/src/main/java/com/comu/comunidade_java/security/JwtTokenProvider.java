package com.comu.comunidade_java.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws; 
import io.jsonwebtoken.JwtBuilder; 
import io.jsonwebtoken.JwtParser; 
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException; 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails; 
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    // Para produção, este segredo DEVE ser forte e configurado externamente (ex: variável de ambiente).
    // Mínimo de 32 bytes (256 bits) para HS256.
    @Value("${jwt.secret:FallbackSuperSecretoParaComUnidadeAppDeveSerLongoESeguro32BytesMinimo}")
    private String jwtSecretString; 

    @Value("${jwt.expirationMs:86400000}") // 24 horas
    private int jwtExpirationMs;

    private SecretKey signingKey; 
    private JwtParser jwtParser;  

    @PostConstruct
    public void init() {
        byte[] keyBytes = jwtSecretString.getBytes(StandardCharsets.UTF_8);
        // O algoritmo HS256 requer uma chave de pelo menos 256 bits (32 bytes).
        if (keyBytes.length < 32) { 
            logger.warn("!!! AVISO DE SEGURANÇA: O 'jwt.secret' configurado é muito curto (< 32 bytes UTF-8) para o algoritmo HS256.");
            logger.warn("!!! A gerar uma chave aleatória FORTE para HS256 para USO EM DESENVOLVIMENTO APENAS.");
            logger.warn("!!! NÃO USE ESTA CONFIGURAÇÃO EM PRODUÇÃO SEM UM 'jwt.secret' FORTE E EXTERNALIZADO.");
            this.signingKey = Jwts.SIG.HS256.key().build(); // Gera uma nova chave segura para HS256
        } else {
            this.signingKey = Keys.hmacShaKeyFor(keyBytes); // Cria a chave a partir do segredo fornecido
        }

        // Pré-configura o parser com a chave de assinatura
        this.jwtParser = Jwts.parser().verifyWith(this.signingKey).build();
    }

    public String generateToken(Authentication authentication) {
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal(); 
        
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

        String roles = authentication.getAuthorities().stream()
                         .map(GrantedAuthority::getAuthority)
                         .collect(Collectors.joining(","));

        JwtBuilder builder = Jwts.builder();
        builder.subject(userPrincipal.getUsername()); 
        builder.claim("roles", roles);
        builder.issuedAt(now); 
        builder.expiration(expiryDate); 
        // O método signWith(SecretKey) infere o algoritmo (HS256, HS384, ou HS512)
        // com base no tamanho e tipo da SecretKey.
        builder.signWith(this.signingKey); 

        return builder.compact();
    }

    public String getUsernameFromJWT(String token) {
        Jws<Claims> claimsJws = this.jwtParser.parseSignedClaims(token); 
        return claimsJws.getPayload().getSubject(); 
    }

    public boolean validateToken(String authToken) {
        try {
            this.jwtParser.parseSignedClaims(authToken); 
            return true;
        } catch (SignatureException ex) { 
            logger.error("Assinatura JWT inválida: {}", ex.getMessage());
        } catch (MalformedJwtException ex) {
            logger.error("Token JWT malformado: {}", ex.getMessage());
        } catch (ExpiredJwtException ex) {
            logger.error("Token JWT expirado: {}", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            logger.error("Token JWT não suportado: {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            logger.error("Argumento JWT inválido (token nulo ou claims vazias): {}", ex.getMessage());
        }
        return false;
    }
}