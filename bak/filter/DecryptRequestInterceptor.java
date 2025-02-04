package com.pzen.server.filter;

import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.SM2;
import cn.hutool.crypto.symmetric.SM4;
import com.pzen.server.annotation.DecryptRequest;
import com.pzen.server.config.SkyConfigInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class DecryptRequestInterceptor implements HandlerInterceptor {

    private final SM2 sm2;
    private final SkyConfigInfo skyConfigInfo;

    public DecryptRequestInterceptor(SkyConfigInfo skyConfigInfo) {
        this.skyConfigInfo = skyConfigInfo;
        // 使用生成的公钥和私钥
        String publicKeyBase64 = skyConfigInfo.getSmCrypt().getPublicKey();
        String privateKeyBase64 = skyConfigInfo.getSmCrypt().getPrivateKey();
        this.sm2 = new SM2(privateKeyBase64, publicKeyBase64);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            if (handlerMethod.getMethodAnnotation(DecryptRequest.class) != null) {
                // 获取请求参数并进行解密
                String encryptedData = request.getParameter("data");
                if (encryptedData != null) {
                    // Split the combined data
                    String[] parts = encryptedData.split(":");
                    if (parts.length == 2) {
                        String encryptedSm4Key = parts[0];
                        String encryptedDataPart = parts[1];

                        // Decrypt the SM4 key using SM2 private key
                        String sm4Key = sm2.decryptStr(encryptedSm4Key, KeyType.PrivateKey);

                        // Decrypt the data using SM4 key
                        String decryptedData = new SM4(sm4Key.getBytes()).decryptStr(encryptedDataPart);

                        // 将解密后的数据放入请求中
                        request.setAttribute("decryptedData", decryptedData);
                    }
                }
            }
        }
        return true;
    }
}
