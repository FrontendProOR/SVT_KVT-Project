package fitpass.fitpass.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenUtils {
    Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    String secret = Encoders.BASE64.encode(key.getEncoded());

//    @Value("${jwt.secret}")
//    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    @PostConstruct
    public void init() {
        byte[] decodedKey = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(decodedKey);
    }

    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(this.secret)
                    .parseClaimsJws(token).getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    public String getEmailFromToken(String token) {
        String email;
        try {
            Claims claims = this.getClaimsFromToken(token);
            email = claims.getSubject();
        } catch (Exception e) {
            email = null;
        }
        return email;
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String email = getEmailFromToken(token);
        return (email != null && email.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        final Date expirationDate = getExpirationDateFromToken(token);
        return expirationDate.before(new Date());
    }

    public Date getExpirationDateFromToken(String token) {
        Date expirationDate;
        try {
            final Claims claims = this.getClaimsFromToken(token);
            expirationDate = claims.getExpiration();
        } catch (Exception e) {
            expirationDate = null;
        }
        return expirationDate;
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", userDetails.getUsername());
        claims.put("role", userDetails.getAuthorities().toArray()[0]);
        claims.put("created", new Date());
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public int getExpiredIn() {
        return expiration.intValue();
    }
}