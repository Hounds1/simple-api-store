package api.exam.io.read.info.domain.member.application;

import api.exam.io.read.info.domain.member.dto.SimpleMemberResponse;
import api.exam.io.read.info.domain.member.domain.persist.Member;
import api.exam.io.read.info.domain.member.domain.persist.MemberRepository;
import api.exam.io.read.info.domain.member.error.AlreadyPausedMemberException;
import api.exam.io.read.info.domain.member.error.MemberNotFoundException;
import api.exam.io.read.info.global.error.ErrorCode;
import api.exam.io.read.info.global.security.principal.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static api.exam.io.read.info.global.error.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    public SimpleMemberResponse create(final Member member) {
        String encodePass = passwordEncoder.encode(member.getPassword());
        member.setEncodePass(encodePass);

        Member savedMember = memberRepository.save(member);
        return SimpleMemberResponse.of(savedMember);
    }

    public void unActivated(final CustomUserDetails principal) {
        Member findMember = memberRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND));

        if (findMember.isActivated()) {
            findMember.unActivated();
        } else throw new AlreadyPausedMemberException(ALREADY_PAUSED_ACCOUNT);
    }
}
