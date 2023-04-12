package api.exam.io.read.info.domain.auth.application;

import api.exam.io.read.info.domain.auth.dto.AuthRequest;
import api.exam.io.read.info.domain.auth.error.AuthInfoMismatchException;
import api.exam.io.read.info.domain.member.domain.persist.Member;
import api.exam.io.read.info.domain.member.domain.persist.MemberRepository;
import api.exam.io.read.info.domain.member.error.MemberNotFoundException;
import api.exam.io.read.info.global.error.ErrorCode;
import api.exam.io.read.info.global.jwt.TokenProvider;
import api.exam.io.read.info.global.jwt.dto.TokenDTO;
import api.exam.io.read.info.global.jwt.vo.AccessToken;
import api.exam.io.read.info.global.jwt.vo.RefreshToken;
import api.exam.io.read.info.global.security.principal.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static api.exam.io.read.info.global.error.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AuthService {

    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder managerBuilder;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    public TokenDTO auth(final AuthRequest authRequest) throws AuthInfoMismatchException {
        Member findMember = memberRepository.findByUsername(authRequest.getUsername())
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND));

        if (passwordEncoder.matches(authRequest.getPassword(), findMember.getPassword())) {
            CustomUserDetails userDetails = memberRepository.findDetailsByUsername(authRequest.getUsername())
                    .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND));

            UsernamePasswordAuthenticationToken token
                    = new UsernamePasswordAuthenticationToken(userDetails, passwordEncoder.encode(authRequest.getPassword()));

            Authentication authenticate = managerBuilder.getObject().authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authenticate);

            return tokenProvider.createToken(userDetails.getUsername(), authenticate);
        } else throw new AuthInfoMismatchException(AUTH_INFO_MISMATCHED);
    }
    public AccessToken reissue(final String refreshToken) {
        if (!tokenProvider.validateToken(refreshToken)) {
            throw new IllegalStateException("토큰 검증 과정에서 예외 발생");
        }

        Authentication authentication = tokenProvider.getAuthentication(refreshToken);
        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return tokenProvider.createToken(principal.getUsername(), authentication).getAccessToken();
    }
}
