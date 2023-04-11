package api.exam.io.read.info.global.jwt;

import api.exam.io.read.info.global.security.principal.CustomUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
