package com.pzen.server.aspect;

import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.SM2;
import cn.hutool.json.JSONObject;
import com.pzen.server.config.SkyConfigInfo;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Aspect
@Component
public class DecryptRequestAspect {

    private static final Logger logger = LoggerFactory.getLogger(DecryptRequestAspect.class);
    @Autowired
    private SkyConfigInfo skyConfigInfo;

    @Around("@annotation(com.pzen.server.annotation.DecryptRequest)")
    public Object decryptRequest(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取方法签名和参数
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        Object[] args = joinPoint.getArgs();
        // 获取参数注解
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        for (int i = 0; i < args.length; i++) {
            // 检查是否有@RequestBody注解
            boolean hasRequestBody = Arrays.stream(parameterAnnotations[i])
                    .anyMatch(annotation -> annotation instanceof RequestBody);
            if (hasRequestBody && args[i] instanceof String) {
                // 如果是字符串类型的请求体，直接解密
                String encryptedData = (String) args[i];
                String decryptedData = decrypt(encryptedData);
                args[i] = decryptedData;
                logger.info("Decrypted request body parameter {}: {} -> {}", i, encryptedData, decryptedData);
            } else if (hasRequestBody && args[i] instanceof JSONObject) {
                // 如果是JsonNode类型的请求体，递归解密
                JSONObject jo = (JSONObject) args[i];
                String data = jo.getStr("data","");
                String decryptedData = decrypt(data);
                // 将解密后的字符串转换回 JSONObject
                JSONObject decryptedJo = new JSONObject();
                decryptedJo.set("data", decryptedData);
                args[i] = decryptedJo;
                logger.info("Decrypted JSON Node parameter {}: {}", i,  data, decryptedData);
            }
        }
        // 执行目标方法并返回结果
        return joinPoint.proceed(args);
    }

    private String decrypt(String encryptedData) {
        if (encryptedData == null || encryptedData.isEmpty()) {
            logger.warn("Encrypted data is null or empty.");
            return "";
        }
        try {
            SM2 sm2 = new SM2(skyConfigInfo.getSmCrypt().getPrivateKey(), skyConfigInfo.getSmCrypt().getPublicKey());
            byte[] decryptedBytes = sm2.decrypt(encryptedData, KeyType.PrivateKey);
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            logger.error("Error during decryption: " + e.getMessage(), e);
            throw new RuntimeException("Decryption failed", e);
        }
    }



}
