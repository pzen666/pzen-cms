package com.pzen.server.filter;

import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.SM2;
import com.pzen.server.annotation.EncryptResponse;
import com.pzen.server.config.SkyConfigInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.io.PrintWriter;

@Component
public class EncryptResponseInterceptor implements HandlerInterceptor {

    private final SM2 sm2;
    private final SkyConfigInfo skyConfigInfo;

    public EncryptResponseInterceptor(SkyConfigInfo skyConfigInfo) {
        this.skyConfigInfo = skyConfigInfo;
        // 使用生成的公钥和私钥
        String publicKeyBase64 = skyConfigInfo.getSmCrypt().getPublicKey();
        String privateKeyBase64 = skyConfigInfo.getSmCrypt().getPrivateKey();
        this.sm2 = new SM2(privateKeyBase64, publicKeyBase64);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            if (handlerMethod.getMethodAnnotation(EncryptResponse.class) != null) {
                // 获取响应内容
                String responseBody = (String) modelAndView.getModel().get("responseBody");
                if (responseBody != null) {
                    // 使用SM2公钥加密响应数据
                    String encryptedData = sm2.encryptBase64(responseBody, KeyType.PublicKey);
                    // 设置加密后的响应内容
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    PrintWriter writer = response.getWriter();
                    writer.write(encryptedData);
                    writer.flush();
                }
            }
        }
    }
}
