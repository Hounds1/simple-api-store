package api.exam.io.read.info.domain.auth.api;

import api.exam.io.read.info.domain.auth.application.AuthService;
import api.exam.io.read.info.domain.auth.dto.AuthRequest;
import api.exam.io.read.info.domain.auth.dto.SimpleAuthResponse;
import api.exam.io.read.info.domain.auth.error.AuthInfoMismatchException;
import api.exam.io.read.info.global.jwt.dto.TokenDTO;
import api.exam.io.read.info.global.jwt.vo.RefreshToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Slf4j
public class AuthController {

    private final AuthService authService;

    @PostMapping("/public/auth")
    public ResponseEntity<SimpleAuthResponse> auth(@RequestBody AuthRequest authRequest) throws AuthInfoMismatchException {
        TokenDTO token = authService.auth(authRequest);
        RefreshToken refreshToken = token.getRefreshToken();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, getCookie(refreshToken).toString())
                .body(SimpleAuthResponse.from(token.getAccessToken(), true));
    }

    @PostMapping("/reissue")
    public ResponseEntity<SimpleAuthResponse> reissue(@CookieValue(name = "refreshToken") String refreshToken) {
        log.info("refreshToken val : [{}]", refreshToken);
        return ResponseEntity.ok().body(SimpleAuthResponse.from(authService.reissue(refreshToken), true));
    }

    public ResponseCookie getCookie(RefreshToken refreshToken) {
        return ResponseCookie.from("refreshToken", refreshToken.refreshToken())
                .httpOnly(true)
                .path("/")
                .maxAge(10000)
                .build();
    }
}
