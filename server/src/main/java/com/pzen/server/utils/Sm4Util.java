package com.pzen.server.utils;

import cn.hutool.core.util.HexUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.symmetric.SM4;

import javax.crypto.SecretKey;

/**
 * SM4加解密工具类
 */
public class Sm4Util {

    /**
     * 获取sm4秘钥
     *
     * @return sm4秘钥
     */
    public static String getSm4Key() {
        SM4 sm4 = SmUtil.sm4();
        SecretKey secretKey = sm4.getSecretKey();
        byte[] encoded = secretKey.getEncoded();
        return HexUtil.encodeHexStr(encoded);
    }

    /**
     * sm4加密
     *
     * @param content 文本
     * @param sm4Key  sm4秘钥
     * @return 加密后的结果
     */
    public static String encrypt(String content, String sm4Key) {
        SM4 sm4 = new SM4(HexUtil.decodeHex(sm4Key));
        return sm4.encryptHex(content);
    }

    /**
     * @param content
     * @param sm4Key
     * @return 解密后的结果
     */
    public static String decrypt(String content, String sm4Key) {
        SM4 sm4 = new SM4(HexUtil.decodeHex(sm4Key));
        return sm4.decryptStr(content);
    }



    // 原文本
    public static void main(String[] args) {
        // 原文本
        String content = "你好 sm4";

        // 获取秘钥
        String sm4Key = Sm4Util.getSm4Key();
        System.out.println("sm4秘钥:" + sm4Key);

        // 加密
        String encrypt = Sm4Util.encrypt(content, sm4Key);
        System.out.println("加密后：" + encrypt);

        // 解密
        String decrypt = Sm4Util.decrypt(encrypt, sm4Key);
        System.out.println("解密后：" + decrypt);
    }


}
