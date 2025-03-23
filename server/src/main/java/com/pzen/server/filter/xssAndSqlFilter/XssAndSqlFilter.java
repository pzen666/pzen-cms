package com.pzen.server.filter.xssAndSqlFilter;


import com.alibaba.fastjson2.JSON;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author pzen
 */
public class XssAndSqlFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(XssAndSqlFilter.class);

    @Override
    public void destroy() {
        // TODO Auto-generated method stub
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String method = "GET";
        String param = "";
        XssAndSqlHttpServletRequestWrapper xssRequest = null;
        if (request instanceof HttpServletRequest) {
            method = ((HttpServletRequest) request).getMethod();
            xssRequest = new XssAndSqlHttpServletRequestWrapper((HttpServletRequest) request);
        }
        if ("POST".equalsIgnoreCase(method) || "PUT".equalsIgnoreCase(method) || "DELETE".equalsIgnoreCase(method)) {
            param = this.getBodyString(xssRequest.getReader());
            if (StringUtils.isNotBlank(param)) {
                if (xssRequest.checkXSSAndSql(param)) {
                    HttpServletResponse httpServletResponse = (HttpServletResponse) response;
                    Map<String, Object> responseMap = new HashMap<>();
                    responseMap.put("code", "748");
                    responseMap.put("message", "所访问的页面请求中有违反安全规则元素存在，拒绝访问!");
                    responseMap.put("success", false);
                    httpServletResponse.setStatus(HttpStatus.OK.value());
                    httpServletResponse.setContentType("application/json;charset=UTF-8");
                    response.getWriter().write(JSON.toJSONString(responseMap));
                    response.getWriter().flush();
                    response.getWriter().close();
                    return;
                }
            }
        }
        if (xssRequest.checkParameter()) {
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("code", "748");
            responseMap.put("message", "所访问的页面请求中有违反安全规则元素存在，拒绝访问!");
            responseMap.put("success", false);
            httpServletResponse.setStatus(HttpStatus.OK.value());
            httpServletResponse.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(JSON.toJSONString(responseMap));
            response.getWriter().flush();
            response.getWriter().close();
            return;
        }
        chain.doFilter(xssRequest, response);
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        // TODO Auto-generated method stub
    }

    // 获取request请求body中参数
    public static String getBodyString(BufferedReader br) {
        String inputLine;
        StringBuilder str = new StringBuilder();
        try {
            while ((inputLine = br.readLine()) != null) {
                str.append(inputLine);
            }
            br.close();
        } catch (IOException e) {
            logger.error("IOException: {0}", e);
        }
        return str.toString();
    }

}
