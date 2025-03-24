package com.pzen.server.utils;

import com.pzen.entity.User;
import com.pzen.server.config.properties.JwtProperties;
import io.ebean.DB;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    @Autowired
    private JwtProperties jwtProperties;
    private PrivateKey privateKey;
    private PublicKey publicKey;

    public JwtUtil() throws Exception {
        this.privateKey = loadPrivateKey(System.getProperty("user.dir") + "/path/key/private.key");
        this.publicKey = loadPublicKey(System.getProperty("user.dir") + "/path/key/public.key");
    }

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
                .setSigningKey(publicKey)
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
        return createToken(claims, u.getUserName(), privateKey);
    }

    public String generateRefreshToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username, privateKey);
    }

    // 使用声明生成 JWT 令牌
    private String createToken(Map<String, Object> claims, String subject, PrivateKey privateKey) {
        return Jwts.builder().claims(claims).subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtProperties.expirationTime))
                .signWith(SignatureAlgorithm.ES256, privateKey)
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

    public PrivateKey loadPrivateKey(String filename) throws Exception {
        byte[] keyBytes = Files.readAllBytes(Paths.get(filename));
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("EC");
        return kf.generatePrivate(spec);
    }

    public PublicKey loadPublicKey(String filename) throws Exception {
        byte[] keyBytes = Files.readAllBytes(Paths.get(filename));
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("EC");
        return kf.generatePublic(spec);
    }

}
