package com.xhh.demo.lettuce.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;

import java.time.LocalDateTime;
import java.util.Objects;

public class JwtTokenTest {
    Payload payload = new Payload();
    private String token;
    private LocalDateTime issueAt;
    private LocalDateTime expire;

    @BeforeEach
    public void beforeEach() {
        payload.setUser("tom")
                .addResource(
                        new Payload.Privilege()
                                .setResource("crm.shop")
                                .setType("project")
                                .setRole_id("developer"))
                .addResource(
                        new Payload.Privilege()
                                .setResource("crm.shop")
                                .setType("project")
                                .setRole_id("manager"));
        issueAt = LocalDateTime.now().withNano(0);
        expire = issueAt.plusDays(1);
        token = JwtToken.createJwtToken("123456", payload, DateConvert.toDate(issueAt),
                DateConvert.toDate(expire));
    }

    @AfterEach
    public void afterEach() {
        payload = new Payload();
        token = "";
    }

    @Test
    public void testGetClaims() {
        Claims claims = JwtToken.parseJwtToken("123456", token);
        assert claims != null;
        Assertions.assertTrue(issueAt.isEqual(DateConvert.toLocalDateTime(claims.getIssuedAt())));
        Assertions.assertTrue(expire.isEqual(DateConvert.toLocalDateTime(claims.getExpiration())));
    }

    @Test
    public void testJwtTokenWithInvalidSecret() {
        Assertions.assertThrows(SignatureException.class, () -> JwtToken.parseJwtToken("xxxxx", token));
    }

    @Test
    public void testJwtTokenExpried() throws InterruptedException {
        issueAt = LocalDateTime.now().withNano(0);
        expire = issueAt.plusSeconds(1);
        String token = JwtToken.createJwtToken("123456", payload, DateConvert.toDate(issueAt),
                DateConvert.toDate(expire));
        Thread.sleep(1001);
        Assertions.assertThrows(ExpiredJwtException.class, () -> JwtToken.parseJwtToken("123456", token));
    }

    @Test
    public void testGetPayload() throws JsonProcessingException {
        Payload result = JwtToken.getPayload(token);
        Assertions.assertEquals("tom", result.getUser());
        Assertions.assertEquals(2, result.getPrivileges().size());
    }

    @Test
    public void testGetExpire() throws JsonProcessingException {
        long actual = JwtToken.getExpireInSec(token);
        Assertions.assertEquals(DateConvert.toDate(expire).getTime() / 1000, actual);
    }

    @Test
    public void testGetTokenFromHead() {
        HttpHeaders httpHeaders = new HttpHeaders();
        String token = JwtToken.getTokenFromHead(httpHeaders);
        Assertions.assertNull(token);
        JwtToken.setToken2Head(httpHeaders, "a.b.c");

        String value = Objects.requireNonNull(httpHeaders.get("Authorization")).get(0);
        Assertions.assertTrue(value.startsWith("Bearer"));

        token = JwtToken.getTokenFromHead(httpHeaders);
        Assertions.assertEquals("a.b.c", token);


    }
}
