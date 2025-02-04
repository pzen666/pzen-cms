package com.pzen.server.aspect;

import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.SM2;
import cn.hutool.json.JSONObject;
import com.pzen.server.config.SkyConfigInfo;
import com.pzen.utils.Result;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Aspect
@Component
public class EncryptResponseAspect {

    private static final Logger logger = LoggerFactory.getLogger(EncryptResponseAspect.class);
    @Autowired
    private SkyConfigInfo skyConfigInfo;

    @Around("@annotation(com.pzen.server.annotation.EncryptResponse)")
    public Object encryptResponse(ProceedingJoinPoint joinPoint) throws Throwable {
        // 执行目标方法并获取结果
        Object result = joinPoint.proceed();
        if (result == null) {
            // 处理返回值为 null 的情况
            logger.error("Return value is null");
            return Result.error("Internal server error");
        }
        // 检查返回值是否为字符串或 JSONObject 类型
        if (result instanceof String originalData) {
            String encryptedData = encrypt(originalData);
            logger.info("Encrypted response: {} -> {}", originalData, encryptedData);
            return encryptedData;
        } else if (result instanceof JSONObject) {
            JSONObject jo = (JSONObject) result;
            String data = jo.toString();
            String encryptedData = encrypt(data);
            JSONObject encryptedJo = new JSONObject();
            encryptedJo.set("data", encryptedData);
            logger.info("Encrypted JSON Node response: {} -> {}", data, encryptedData);
            return encryptedJo;
        } else if (result instanceof Result<?> resultObj) {
            int code = resultObj.getCode();
            String errorMsg = resultObj.getMessage();
            if (resultObj.getData() instanceof String) {
                if (code == 200) {
                    String originalData = (String) resultObj.getData();
                    String encryptedData = encrypt(originalData);
                    logger.info("Encrypted response: {} -> {}", originalData, encryptedData);
                    return Result.success(encryptedData);
                } else {
                    return Result.failure(code, errorMsg);
                }
            } else if (resultObj.getData() instanceof JSONObject) {
                if (code == 200) {
                    JSONObject jo = (JSONObject) resultObj.getData();
                    String data = jo.toString();
                    String encryptedData = encrypt(data);
                    logger.info("Encrypted JSON Node response: {} -> {}", data, encryptedData);
                    return Result.success(resultObj);
                } else {
                    return Result.failure(code, errorMsg);
                }

            }
        }
        // 如果不是需要加密的类型，直接返回原结果
        return Result.error("Unexpected return type");
    }


    private String encrypt(String data) {
        if (data == null || data.isEmpty()) {
            logger.warn("Data to encrypt is null or empty.");
            return "";
        }
        try {
            SM2 sm2 = new SM2(skyConfigInfo.getSmCrypt().getPrivateKey(), skyConfigInfo.getSmCrypt().getPublicKey());
            byte[] encryptedBytes = sm2.encrypt(data.getBytes(StandardCharsets.UTF_8), KeyType.PublicKey);
            return bytesToHex(encryptedBytes);
        } catch (Exception e) {
            logger.error("Error during encryption: {}", e.getMessage(), e);
            throw new RuntimeException("Encryption failed", e);
        }
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        if (sb.length() > 2) {
//            sb.delete(0, 2);
        }
        return sb.toString();
    }

}
   