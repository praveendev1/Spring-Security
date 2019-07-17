package com.learn.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import java.util.Collection;

public class JWTAutheticatedToken extends PreAuthenticatedAuthenticationToken {

    public JWTAutheticatedToken(Object aPrincipal, Object aCredentials)
    {
        super(aPrincipal, aCredentials);
    }

    public JWTAutheticatedToken(Object aPrincipal, Object aCredentials, Collection<? extends GrantedAuthority> anAuthorities)
    {
        super(aPrincipal, aCredentials, anAuthorities);
    }

}
