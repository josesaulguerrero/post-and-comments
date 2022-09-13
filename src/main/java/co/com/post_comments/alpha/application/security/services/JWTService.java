package co.com.post_comments.alpha.application.security.services;

import co.com.post_comments.alpha.application.security.models.AppUser;
import co.com.post_comments.alpha.application.security.models.JWT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;

@Service
public class JWTService {
    private final SecretKey secretKey = Keys.hmacShaKeyFor(
            System.getenv("JWT_SECRET_KEY").getBytes()
    );

    private final JwtParser parser = Jwts.parserBuilder()
            .setSigningKey(this.secretKey)
            .build();

    public JWT generateJWTForUserWithUsername(String username) {
        return new JWT(
                Jwts.builder()
                        .setSubject(username)
                        .setExpiration(Date.from(
                                Instant.now().plus(1, ChronoUnit.HOURS)
                        ))
                        .setIssuedAt(new Date())
                        .signWith(this.secretKey)
                        .compact(),
                new ArrayList<>()
        );
    }

    public String getUsernameFromToken(JWT token) {
        return this.parser.parseClaimsJwt(token.value)
                .getBody()
                .getSubject();
    }

    public boolean isValidJWT(JWT token, AppUser user) {
        Claims claims = this.parser.parseClaimsJwt(token.value)
                .getBody();
        return claims.getExpiration().after(Date.from(Instant.now())) && claims.getSubject().equals(user.getUsername());
    }
}
