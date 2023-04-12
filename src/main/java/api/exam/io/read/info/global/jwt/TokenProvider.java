package api.exam.io.read.info.global.jwt;

import api.exam.io.read.info.global.jwt.dto.TokenDTO;
import api.exam.io.read.info.global.jwt.vo.AccessToken;
import api.exam.io.read.info.global.jwt.vo.RefreshToken;
import api.exam.io.read.info.global.security.principal.CustomUserDetails;
import api.exam.io.read.info.global.security.principal.CustomUserDetailsService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class TokenProvider implements InitializingBean {

    private static final String AUTHORITIES_KEY = "role";

    private final String secret;
    private final long accessTokenValidityInMilliseconds;
    private final long refreshTokenValidityInMilliseconds;
    private final CustomUserDetailsService userDetailsService;

    public TokenProvider(@Value("${jwt.secret}") String secret,
                         @Value("${jwt.accessTokenValidityInMilliseconds}") long accessTokenValidityInMilliseconds,
                         @Value("${jwt.refreshTokenValidityInMilliseconds}") long refreshTokenValidityInMilliseconds,
                         CustomUserDetailsService userDetailsService) {
        this.secret = secret;
        this.accessTokenValidityInMilliseconds = accessTokenValidityInMilliseconds;
        this.refreshTokenValidityInMilliseconds = refreshTokenValidityInMilliseconds;
        this.userDetailsService = userDetailsService;
    }

    private Key key;

    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public TokenDTO createToken(String username, Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        log.debug("authorities : [{}]", authorities);

        long now = (new Date()).getTime();

        String accessToken = Jwts.builder()
                .claim("username", username)
                .claim(AUTHORITIES_KEY, authorities)
                .setExpiration(new Date(now + accessTokenValidityInMilliseconds))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        String refreshToken = Jwts.builder()
                .claim("username", username)
                .claim(AUTHORITIES_KEY, authorities)
                .setExpiration(new Date(now + refreshTokenValidityInMilliseconds))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        AccessToken newAccessToken = AccessToken.from(accessToken);
        RefreshToken newRefreshToken = RefreshToken.from(refreshToken);

        return TokenDTO.of(newAccessToken, newRefreshToken);
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        List<? extends SimpleGrantedAuthority> authorities = Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .toList();

        String id = String.valueOf(claims.getId());
        String username = String.valueOf(claims.get("username"));

        CustomUserDetails principal = (CustomUserDetails) userDetailsService.loadUserByUsername(username);

        return new UsernamePasswordAuthenticationToken(principal, "password", authorities);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key.getEncoded()).build().parseClaimsJws(token);

            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.error("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.error("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.error("지원하지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.error("잘못된 JWT 토큰입니다.");
        }

        return false;
    }
}
