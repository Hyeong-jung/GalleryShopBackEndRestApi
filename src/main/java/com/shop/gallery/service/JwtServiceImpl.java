package com.shop.gallery.service;



import io.jsonwebtoken.*;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
//import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service("jwtService")
public class JwtServiceImpl implements JwtService {

    //private String secretKey = "abbci2ioadij@@@ai17a662###8139!!!18ausudahd178316738687687@@ad6g";
    //private String secretKey = "abcdefghijk0123456789!@#";
    private String secretKey = "0123456789_ABCDEFGHIJKLMNOPQRSTUVWXYZ!@#$"; // 최소 32자리 이상


    @Override
    public String getToken(String key, Object value) {

        Date expTime = new Date();
        expTime.setTime(expTime.getTime() + 1000 * 60 * 30);
        //byte[] secretByteKey = DatatypeConverter.parseBase64Binary(secretKey);

        /* secretKey 를 base64Encode,  base64Decode 하여 사용
        String base64EncodedKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        byte[] secretByteKey = Base64.getDecoder().decode(base64EncodedKey);
        Key signKey = new SecretKeySpec(secretByteKey, SignatureAlgorithm.HS256.getJcaName());
         */

        Key signKey = new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS256.getJcaName());

        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("typ", "JWT");
        headerMap.put("alg", "HS256");

        Map<String, Object> map = new HashMap<>();
        map.put(key, value);

        JwtBuilder builder = Jwts.builder().setHeader(headerMap)
                .setClaims(map)
                .setExpiration(expTime)
                .signWith(signKey, SignatureAlgorithm.HS256);

        return builder.compact();
    }

    @Override
    public Claims getClaims(String token) {
        if (token != null && !"".equals(token)) {
            try {
                /* secretKey 를 base64Encode,  base64Decode 하여 사용
                //byte[] secretByteKey = DatatypeConverter.parseBase64Binary(secretKey);
                byte[] secretByteKey = Base64.getDecoder().decode(secretKey);
                Key signKey = new SecretKeySpec(secretByteKey, SignatureAlgorithm.HS256.getJcaName());
                 */

                Key signKey = new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS256.getJcaName());
                return Jwts.parserBuilder().setSigningKey(signKey).build().parseClaimsJws(token).getBody();
            } catch (ExpiredJwtException e) {
                // JWT 만료됨
            } catch (JwtException e) {
                // JWT 유효하지 않음
            }
        }

        return null;
    }

    @Override
    public boolean isValid(String token) {
        return this.getClaims(token) != null;
    }

    @Override
    public int getId(String token) {
        Claims claims = this.getClaims(token);

        if (claims != null) {
            return Integer.parseInt(claims.get("id").toString());
        }

        return 0;
    }
}


/*
import io.jsonwebtoken.*;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.spec.SecretKeySpec;



@Service("jwtService")
public class JwtServiceImpl implements JwtService {

    // 일반 문자열 사용 시 Base64 인코딩 안 해도 되며, getBytes() 사용 가능
    private String secretKey = "abcdefghijk0123456789!@#";

    @Override
    public String getToken(String key, Object value) {
        Date expTime = new Date();
        expTime.setTime(expTime.getTime() + 1000 * 60 * 30); // 30분

        // 문자열을 byte[]로 변환해서 key 생성
        Key signKey = new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS256.getJcaName());

        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("typ", "JWT");
        headerMap.put("alg", "HS256");

        Map<String, Object> claimsMap = new HashMap<>();
        claimsMap.put(key, value);

        return Jwts.builder()
                .setHeader(headerMap)
                .setClaims(claimsMap)
                .setExpiration(expTime)
                .signWith(signKey, SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public Claims getClaims(String token) {
        if (token != null && !token.isEmpty()) {
            try {
                Key signKey = new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS256.getJcaName());
                return Jwts.parserBuilder()
                        .setSigningKey(signKey)
                        .build()
                        .parseClaimsJws(token)
                        .getBody();
            } catch (ExpiredJwtException e) {
                System.out.println("Token expired: " + e.getMessage());
            } catch (JwtException e) {
                System.out.println("Invalid token: " + e.getMessage());
            }
        }
        return null;
    }

    @Override
    public boolean isValid(String token) {
        return this.getClaims(token) != null;
    }

    @Override
    public int getId(String token) {
        Claims claims = this.getClaims(token);
        if (claims != null) {
            return Integer.parseInt(claims.get("id").toString());
        }
        return 0;
    }
}
*/

/* java 1.8 용 코드
import io.jsonwebtoken.*;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
//import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service("jwtService")
public class JwtServiceImpl implements JwtService {

    //private String secretKey = "abbci2ioadij@@@ai17a662###8139!!!18ausudahd178316738687687@@ad6g";
    private String secretKey = "abcdefghijk0123456789!@#";

    @Override
    public String getToken(String key, Object value) {

        Date expTime = new Date();
        expTime.setTime(expTime.getTime() + 1000 * 60 * 30);
        //byte[] secretByteKey = DatatypeConverter.parseBase64Binary(secretKey);
        byte[] secretByteKey = Base64.getDecoder().decode(secretKey);
        Key signKey = new SecretKeySpec(secretByteKey, SignatureAlgorithm.HS256.getJcaName());

        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("typ", "JWT");
        headerMap.put("alg", "HS256");

        Map<String, Object> map = new HashMap<>();
        map.put(key, value);

        JwtBuilder builder = Jwts.builder().setHeader(headerMap)
                .setClaims(map)
                .setExpiration(expTime)
                .signWith(signKey, SignatureAlgorithm.HS256);

        return builder.compact();
    }

    @Override
    public Claims getClaims(String token) {
        if (token != null && !"".equals(token)) {
            try {
                //byte[] secretByteKey = DatatypeConverter.parseBase64Binary(secretKey);
                byte[] secretByteKey = Base64.getDecoder().decode(secretKey);
                Key signKey = new SecretKeySpec(secretByteKey, SignatureAlgorithm.HS256.getJcaName());
                return Jwts.parserBuilder().setSigningKey(signKey).build().parseClaimsJws(token).getBody();
            } catch (ExpiredJwtException e) {
                // 만료됨
            } catch (JwtException e) {
                // 유효하지 않음
            }
        }

        return null;
    }

    @Override
    public boolean isValid(String token) {
        return this.getClaims(token) != null;
    }

    @Override
    public int getId(String token) {
        Claims claims = this.getClaims(token);

        if (claims != null) {
            return Integer.parseInt(claims.get("id").toString());
        }

        return 0;
    }
}
java 1.8 용 코드 */