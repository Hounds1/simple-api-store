package api.exam.io.read.info.domain.auth.dto;

import api.exam.io.read.info.global.jwt.vo.AccessToken;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class SimpleAuthResponse {

    private AccessToken accessToken;

    private boolean result;

    public static SimpleAuthResponse from(final AccessToken accessToken, final boolean result) {
        return new SimpleAuthResponse(accessToken, result);
    }
}
