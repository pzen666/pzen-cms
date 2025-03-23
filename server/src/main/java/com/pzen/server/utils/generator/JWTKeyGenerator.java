package com.pzen.server.utils.generator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

public class JWTKeyGenerator {

    public static void main(String[] args) throws Exception {
//        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
//        keyPairGenerator.initialize(2048);
       KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
        keyPairGenerator.initialize(256);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();
        // 获取项目根目录
        String path = System.getProperty("user.dir") + "/path/key";
        // 创建目录
        File keysDirectory = new File(path);
        if (!keysDirectory.exists()) {
            keysDirectory.mkdirs();
        }
        saveKeyToFile(privateKey, path + "/private.key");
        saveKeyToFile(publicKey, path + "/public.key");
    }

    private static void saveKeyToFile(java.security.Key key, String fileName) throws IOException {
        byte[] keyBytes = key.getEncoded();
        FileOutputStream fos = new FileOutputStream(fileName);
        fos.write(keyBytes);
        fos.close();
    }
}

