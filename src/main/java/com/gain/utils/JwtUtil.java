package com.gain.utils;

import com.gain.domain.configPo.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final JwtProperties jwtProperties;

    /**
     * 生成用户 JWT Token
     * @param claims 设置的信息
     */
    public String createUserToken(Map<String, Object> claims) {
        return createJWT(jwtProperties.getUserSecretKey(), jwtProperties.getUserTtl(), claims);
    }

    /**
     * 生成 jwt
     * @param secretKey jwt 秘钥 (注意：HS256 算法要求秘钥长度至少为 32 位字符串)
     * @param ttlMillis jwt 过期时间 (毫秒)
     * @param claims    设置的信息
     */
    public static String createJWT(String secretKey, long ttlMillis, Map<String, Object> claims) {
        // 将字符串秘钥转换为安全 Key 对象
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        long expMillis = System.currentTimeMillis() + ttlMillis;
        Date exp = new Date(expMillis);

        return Jwts.builder()
                .claims(claims) // 设置自定义负载
                .expiration(exp) // 设置过期时间
                .signWith(key)   // 使用 Key 对象签名，自动识别算法
                .compact();
    }

    /**
     * Token 解密
     * @param secretKey jwt 秘钥
     * @param token jwt token
     */
    public static Claims parseJWT(String secretKey, String token) {
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        return Jwts.parser()
                .verifyWith(key) // 验证秘钥
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
