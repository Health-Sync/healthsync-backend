package AI.Powered.Virtual.Medical.Doctor.security;

import AI.Powered.Virtual.Medical.Doctor.entity.User;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtToken {
    private final String secret_key = "secretKey";
    private long accessTokenValidity = 24*60*60*1000;

    private final JwtParser jwtParser;

    private final  String TOKEN_HEADER =  "Authorization";
    private final String TOKEN_PREFIX = "Bearer ";

    public JwtToken(){
        this.jwtParser = Jwts.parser().setSigningKey(secret_key);
    }

    public String createToken(User user){
        Claims claims = Jwts.claims().setSubject(user.getEmail());
        Date tokenCreateTime = new Date(System.currentTimeMillis());
        Date tokenValidity = new Date(tokenCreateTime.getTime() + accessTokenValidity);
        return Jwts.builder()
                .setIssuedAt(tokenCreateTime)
                .setClaims(claims)
                .setExpiration(tokenValidity)
                .signWith(SignatureAlgorithm.HS256, secret_key)
                .compact();
    }

    private Claims parseJwtClaims(String token){
        return jwtParser.parseClaimsJws(token).getBody();
    }

    public Claims resolveClaims(HttpServletRequest servRequest){
        try {
            String token = resolveToken(servRequest);
            if(token != null){
                return parseJwtClaims(token);
            }
            return null;
        }catch (ExpiredJwtException ex){
            servRequest.setAttribute("expired", ex.getMessage());
            throw ex;
        }catch (Exception ex){
            servRequest.setAttribute("invalid", ex.getMessage());
            throw ex;
        }
    }

    public String resolveToken(HttpServletRequest request){
        String bearerToken = request.getHeader(TOKEN_HEADER);
        if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_PREFIX.length());
        }
        return null;
    }

    public boolean validateClaims(String token){
        return parseJwtClaims(token).getExpiration().after(new Date());
    }

    public String getEmail(String token) {
        return parseJwtClaims(token).getSubject();
    }

}
