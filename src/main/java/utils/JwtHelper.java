package utils;

import io.jsonwebtoken.*;
import lombok.extern.java.Log;
import models.User;

import javax.servlet.http.HttpServletRequest;

import static io.jsonwebtoken.lang.Strings.hasText;

@Log
public class JwtHelper {

    private final String jwtSecret = "123123";
    private static final String AUTHORIZATION = "Authorization";

    public String generateToken(User user) {
        Claims claims = Jwts.claims();
        claims.put("email", user.getEmail());
        claims.put("password", user.getPassword());
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException expEx) {
            log.severe("Token expired");
        } catch (UnsupportedJwtException unsEx) {
            log.severe("Unsupported jwt");
        } catch (MalformedJwtException mjEx) {
            log.severe("Malformed jwt");
        } catch (SignatureException sEx) {
            log.severe("Invalid signature");
        } catch (Exception e) {
            log.severe("invalid token");
        }
        return false;
    }

    public String getEmailFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return (String) claims.get("email");
    }

    public String getPasswordFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return (String) claims.get("password");
    }

    public String getTokenFromRequest(HttpServletRequest request) {
        String fullHeader = request.getHeader(AUTHORIZATION);
        if (hasText(fullHeader) && fullHeader.startsWith("Bearer ")) {
            return fullHeader.substring(7);
        }
        return null;
    }
}