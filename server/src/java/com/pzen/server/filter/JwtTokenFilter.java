package com.pzen.server.filter;

import com.pzen.entity.User;
import com.pzen.server.config.SkyConfigInfo;
import com.pzen.server.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private SkyConfigInfo skyConfigInfo;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String uri = request.getRequestURI();
        String method = request.getMethod();
        // 排除特定路径
        // 允许所有 GET 请求匿名访问
        if ("GET".equalsIgnoreCase(method)) {
            filterChain.doFilter(request, response);
            return;
        }
        List<String> openUri = skyConfigInfo.getFilter().getOpenUri();
        for (String path : openUri) {
            if (pathMatcher.match(path, uri)) {
                filterChain.doFilter(request, response);
                return;
            }
        }
        // 处理 JWT 认证逻辑
        String token = jwtUtil.resolveToken(request);
        if (token != null) {
            User userDetails = jwtUtil.getUserDetailsFromToken(token);
            if (jwtUtil.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }
}


