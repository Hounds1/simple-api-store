package api.exam.io.read.info.domain.member.application;

import api.exam.io.read.info.domain.member.dto.SimpleMemberResponse;
import api.exam.io.read.info.domain.member.domain.persist.Member;
import api.exam.io.read.info.domain.member.domain.persist.MemberRepository;
import api.exam.io.read.info.domain.member.error.AlreadyJoinedMemberException;
import api.exam.io.read.info.domain.member.error.AlreadyPausedMemberException;
import api.exam.io.read.info.domain.member.error.MemberNotFoundException;
import api.exam.io.read.info.global.security.principal.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    public SimpleMemberResponse create(final Member member) {

        Optional<Member> findMember = memberRepository.findByUsername(member.getUsername());

        if (findMember.isPresent() && findMember.get().isActivated()) {
            throw new AlreadyJoinedMemberException("이미 가입된 아이디입니다.");
        }

        String encodePass = passwordEncoder.encode(member.getPassword());

        if (findMember.isPresent() && !findMember.get().isActivated()) {
            memberRepository.delete(findMember.get());

            member.setEncodePass(encodePass);

            Member savedMember = memberRepository.save(member);
            return SimpleMemberResponse.of(savedMember);
        }

        member.setEncodePass(encodePass);

        Member savedMember = memberRepository.save(member);
        return SimpleMemberResponse.of(savedMember);
    }

    public void unActivated(final CustomUserDetails principal) {
        Member findMember = memberRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new MemberNotFoundException("찾을 수 없는 상태의 멤버입니다."));

        if (findMember.isActivated()) {
            findMember.unActivated();
        } else throw new AlreadyPausedMemberException("이미 탈퇴된 회원입니다.");
    }
}
