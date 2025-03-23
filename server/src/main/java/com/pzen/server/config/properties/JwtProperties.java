package com.pzen.server.config.properties;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtProperties {

    @Value("${jwt.secret.jwtType}")
    private String jwtType;
    @Value("${jwt.secret.expirationTime}")
    public long expirationTime;
    @Value("${jwt.secret.expirationRefreshTime}")
    private long expirationRefreshTime;


}
