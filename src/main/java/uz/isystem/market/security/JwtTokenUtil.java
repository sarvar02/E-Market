package uz.isystem.market.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import uz.isystem.market.user.User;

import java.util.Date;

@Component
public class JwtTokenUtil {
    private final String jwtSecret = "sdflkjaweroiuqwerhsdf_isystem_E-market";
    private final String jwtIssuer = "e-market.uz";

    public String generateToken(User user){
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setId(String.valueOf(user.getId()));
        jwtBuilder.setIssuedAt(new Date());
        jwtBuilder.setSubject(String.format("%s %s", user.getId(), user.getContact()));
        jwtBuilder.signWith(SignatureAlgorithm.HS256, jwtSecret);
        jwtBuilder.setExpiration(new Date(System.currentTimeMillis() + (24*60*60*1000)));
        jwtBuilder.setIssuer(jwtIssuer);
        return jwtBuilder.compact();
    }

    public String getId(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject().split(" ")[0];
    }

    public String getContact(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject().split(" ")[1];
    }

    public boolean validate(String token){
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
