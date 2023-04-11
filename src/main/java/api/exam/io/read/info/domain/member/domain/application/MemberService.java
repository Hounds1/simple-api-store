package api.exam.io.read.info.domain.member.domain.application;

import api.exam.io.read.info.domain.member.domain.dto.SimpleMemberResponse;
import api.exam.io.read.info.domain.member.domain.persist.Member;
import api.exam.io.read.info.domain.member.domain.persist.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
