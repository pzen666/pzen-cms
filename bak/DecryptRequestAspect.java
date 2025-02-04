package com.pzen.server.aspect;

import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.SM2;
import com.pzen.server.config.SkyConfigInfo;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
        // 获取方法参数并进行解密
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof String) {
                String encryptedData = (String) args[i];
                String decryptedData = decrypt(encryptedData);
                args[i] = decryptedData;
                logger.info("Decrypted parameter {}: {} -> {}", i, encryptedData, decryptedData);
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
