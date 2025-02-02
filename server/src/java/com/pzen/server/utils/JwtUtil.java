package com.pzen.server.utils;

import com.pzen.entity.User;
import io.ebean.DB;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    @Value("${jwt.secret.secretKey}")
    private String secret;
    @Value("${jwt.secret.expirationTime}")
    private long expirationTime;
    @Value("${jwt.secret.expirationRefreshTime}")
    private long expirationRefreshTime;

    @Autowired
    private UserDetailsService userDetailsService;

    // 从请求中提取 JWT 令牌
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // 去掉 "Bearer " 前缀
        }
        return null;
    }

    // 从 JWT 令牌中提取用户名
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // 从 JWT 令牌中提取过期时间
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // 从 JWT 令牌中提取单个声明
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // 从 JWT 令牌中提取所有声明
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // 检查 JWT 令牌是否过期
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // 生成 JWT 令牌
    public String generateToken(User u) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, u.getUserName());
    }

    public String generateRefreshToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    // 使用声明生成 JWT 令牌
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    // 验证 JWT 令牌
    public Boolean validateToken(String token, User userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUserName()) && !isTokenExpired(token));
    }

    // 从 JWT 令牌中提取用户详细信息
    public User getUserDetailsFromToken(String token) {
        final String username = extractUsername(token);
        // 这里假设你有一个 UserDetailsService 来加载用户详细信息
        return DB.byName("db").find(User.class).where().eq("userName", username).findOne();
    }

    public String refreshToken(String refreshToken) {
        if (!isTokenExpired(refreshToken)) {
            String username = extractUsername(refreshToken);
            return generateRefreshToken(username);
        } else {
            throw new RuntimeException("Refresh token expired");
        }
    }


}
