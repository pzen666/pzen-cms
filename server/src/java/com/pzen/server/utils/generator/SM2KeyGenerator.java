package com.pzen.server.utils.generator;

import cn.hutool.crypto.asymmetric.SM2;

import java.math.BigInteger;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;

public class SM2KeyGenerator {

    public static void main(String[] args) {
        SM2 sm2 = new SM2();
        // 获取公钥和私钥
        PublicKey publicKey = sm2.getPublicKey();
        PrivateKey privateKey = sm2.getPrivateKey();
        String publicKeyBase64 = sm2.getPublicKeyBase64();
        String privateKeyBase64 = sm2.getPrivateKeyBase64();

        // 提取未压缩的公钥
        String unPublicKeyHex = getUnPublicKeyHex((ECPublicKey) publicKey);

        // 提取私钥的十六进制表示
        String unPrivateKeyHex = getUnPrivateKeyHex((ECPrivateKey) privateKey);

        System.out.println("Public Key (Base64): " + publicKeyBase64);
        System.out.println("Private Key (Base64): " + privateKeyBase64);
        System.out.println("Un Public Key (Hex): " + unPublicKeyHex);
        System.out.println("Un Private Key (Hex): " + unPrivateKeyHex);
    }

    private static String getUnPublicKeyHex(ECPublicKey publicKey) {
        BigInteger x = publicKey.getW().getAffineX();
        BigInteger y = publicKey.getW().getAffineY();
        String xHex = bigIntegerToFixedLengthHex(x, 32);
        String yHex = bigIntegerToFixedLengthHex(y, 32);
        return "04" + xHex + yHex;
    }

    private static String getUnPrivateKeyHex(ECPrivateKey privateKey) {
        BigInteger s = privateKey.getS();
        return bigIntegerToFixedLengthHex(s, 32);
    }

    private static String bigIntegerToFixedLengthHex(BigInteger bigInt, int length) {
        String hex = bigInt.toString(16);
        while (hex.length() < length * 2) {
            hex = "0" + hex;
        }
        return hex;
    }
}
