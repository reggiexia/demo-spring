package com.xhh.demo.lettuce.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.http.HttpHeaders;

import javax.crypto.SecretKey;
import java.util.*;

@Log4j2
public class JwtToken {

    public final static SignatureAlgorithm HS512 = SignatureAlgorithm.HS512;

    public static String createJwtToken(String secret, Payload payload, Date issueAt, Date expiration) {
        SecretKey key = convertKey(secret);
        BeanMap map = BeanMap.create(payload);
        return Jwts.builder().setClaims(map)
                .setIssuedAt(issueAt)
                .setExpiration(expiration)
                .signWith(key).compact();
    }

    public static Claims parseJwtToken(String secret, String token) {
        SecretKey key = convertKey(secret);
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        if (claims.getExpiration().getTime() <= System.currentTimeMillis() / 1000) {
            log.error("token has expired");
            return null;
        }
        return claims;
    }

    public static Payload getPayload(String token) throws JsonProcessingException {
        String body = new String(Base64.getDecoder().decode(token.split("\\.")[1]));
        return Json.json2Object(body, Payload.class);
    }
    public static long getExpireInSec(String token) throws JsonProcessingException {
        String body = new String(Base64.getDecoder().decode(token.split("\\.")[1]));
        HashMap<String, Object> map = Json.json2Object(body, HashMap.class);
        Object expObj = map.get("exp");
        if (expObj == null) {
            return 0;
        }
        if (expObj instanceof Number) {
            return ((Number) expObj).longValue();
        }
        if (expObj instanceof String) {
            return Long.parseLong((String) expObj);
        }
        throw new IllegalArgumentException("unrecognized exp time in the jwt 'exp' field");
    }

    private static SecretKey convertKey(String secret) {
        byte[] bytes = secret.getBytes();
        int minKeyLength = HS512.getMinKeyLength();
        if (bytes.length < minKeyLength) {
            byte[] newOne = new byte[minKeyLength];
            System.arraycopy(bytes, 0, newOne, 0, bytes.length);
            bytes = newOne;
        }
        return Keys.hmacShaKeyFor(bytes);
    }

    public static String getTokenFromHead(HttpHeaders headers) {
        List<String> values = headers.get("Authorization");
        if (values == null || values.isEmpty()) {
            return null;
        }

        String value = values.get(0);
        int idx = value.indexOf("Bearer");
        if (idx == -1) {
            throw new IllegalArgumentException(String.format("auth type, %s,  not found", "Bearer"));
        }

        idx += "Bearer".length();
        return value.substring(idx).trim();
    }

    public static void setToken2Head(HttpHeaders headers, String token) {
        headers.remove("Authorization");
        headers.add("Authorization", "Bearer" + " " + token);
    }
}
