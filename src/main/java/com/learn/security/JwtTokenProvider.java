package com.learn.security;

import com.learn.dto.Authority;
import com.learn.dto.UserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    private String secretKey = "3a192d87-986a-4045-a4d8-e42215fb873b";

    private long validityInMilliseconds = 900000; // 15 minutes

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String username, List<String> roles, String designation, String location, String yearsOfExperience) {

        Claims claims = Jwts.claims();
        claims.put("roles", roles);
        claims.put("username", username);
        claims.put("designation", designation);
        claims.put("location", location);
        claims.put("yearsOfExperience", yearsOfExperience);

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secretKey);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject("User Details")
                .setIssuer("Finicity")
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }


    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);

            if (claims.getBody().getExpiration().after(new Date())) {
                return true;
            }
        }
        catch (Exception e) {
            throw new BadCredentialsException("Token is invalid.", e);
        }
        return false;
    }

    public UserDetails extractUserDetails(String token){
        Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);

        Claims body = claims.getBody();
        UserDetails userDetails = new UserDetails();

        userDetails.setUsername(String.valueOf(body.get("username")));

        List<String> roles = (ArrayList)body.get("roles");
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.addAll(roles.stream().map(s -> new Authority(s)).collect(Collectors.toList()));

        userDetails.setAuthorities(authorities);
        userDetails.setDesignation(String.valueOf(body.get("designation")));
        userDetails.setLocation(String.valueOf(body.get("location")));
        userDetails.setYearsOfExperince(String.valueOf(body.get("yearsOfExperience")));
        return userDetails;
    }
}
