package code.uz.utils;

import code.uz.dto.JwtDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {
    private static final int liveTimeToken = 1000 * 3600 * 24;
    private static final long refreshLiveToken = 1000L * 3600 * 24 * 30;
    private static final String secretKey = "cjsbacabsjcblanjxluebcsbdncljkjnuasnnkcusen1e243";


    public static String encode(String phone, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("phone", phone);
        claims.put("role", role);

        return Jwts.builder()
                .claims(claims)
                .subject(phone)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + liveTimeToken))
                .signWith(getSignInKey())
                .compact();
    }

    public static String refreshToken(String phone, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("phone", phone);
        claims.put("role", role);

        return Jwts
                .builder()
                .claims(claims)
                .subject(phone)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + refreshLiveToken))  //uzgargan joyi
                .signWith(getSignInKey())
                .compact();
    }

    private static SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public static JwtDTO decode(String token) {
        Claims claims = Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        String phone = claims.getSubject();
        String role = (String) claims.get("role");
        return new JwtDTO(phone, role);
    }
}
