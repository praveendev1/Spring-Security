package com.learn.security;

import com.learn.dto.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Override
    public Authentication authenticate(Authentication authentication) {
        JWTAutheticatedToken jwtAutheticatedToken;
        UserDetails userDetails = new UserDetails();
        try{
            if(jwtTokenProvider.validateToken((String) authentication.getPrincipal())){
                userDetails = jwtTokenProvider.extractUserDetails((String) authentication.getPrincipal());
            }
            jwtAutheticatedToken = new JWTAutheticatedToken(userDetails.getUsername(), null, userDetails.getAuthorities());

        }
        catch(BadCredentialsException e){
            throw new BadCredentialsException("Token is invalid.", e);
        }
        return jwtAutheticatedToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(PreAuthenticatedAuthenticationToken.class);
    }
}
