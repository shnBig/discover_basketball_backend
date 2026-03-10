package com.gain.utils;

import org.springframework.stereotype.Component;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Component
public class PasswordEncoder {

    /**
     * 生成随机盐值
     * @return 32 位 UUID 盐值
     */
    public String generateSalt() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 对密码进行 MD5 加密（加盐）
     * @param rawPassword 明文密码
     * @param salt 盐值
     * @return 加密后的密码
     */
    public String encode(String rawPassword, String salt) {
        return md5(rawPassword + salt);
    }

    /**
     * 验证密码（加盐）
     * @param rawPassword 明文密码
     * @param salt 盐值
     * @param encodedPassword 加密后的密码
     * @return 是否匹配
     */
    public boolean matches(String rawPassword, String salt, String encodedPassword) {
        String encoded = encode(rawPassword, salt);
        return encoded.equals(encodedPassword);
    }

    /**
     * MD5 加密方法
     */
    private String md5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 加密失败", e);
        }
    }

    /**
     * 简单 MD5 加密（不加盐，用于测试）
     */
    public String md5Simple(String rawPassword) {
        return md5(rawPassword);
    }
}
