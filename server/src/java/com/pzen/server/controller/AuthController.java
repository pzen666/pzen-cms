package com.pzen.server.controller;

import com.pzen.dto.UserDTO;
import com.pzen.entity.User;
import com.pzen.server.utils.JwtUtil;
import com.pzen.utils.Result;
import io.ebean.DB;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/auth")
public class AuthController {


    private final AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    public AuthController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    //生成JWT令牌
    @PostMapping("/authenticate")
    public ResponseEntity<Result<Map<String, String>>> createAuthenticationToken(@RequestBody UserDTO dto) {
        User u = DB.byName("db").find(User.class).where().eq("userName", dto.getUserName()).eq("password", dto.getPassword()).findOne();
        if (u == null) {
            return ResponseEntity.badRequest().body(Result.error("用户名或密码错误"));
        }
        final String jwt = jwtUtil.generateToken(u);
        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", "Bearer " + jwt);
        return ResponseEntity.ok(Result.success(tokens, "JWT Tokens Generated successfully"));
    }


    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        if (token == null) {
            return ResponseEntity.badRequest().body("Refresh token is missing");
        }
        try {
            String newAccessToken = jwtUtil.refreshToken(token);
            return ResponseEntity.ok().body("Bearer " + newAccessToken);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}

