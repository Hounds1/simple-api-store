package api.exam.io.read.info.domain.member.application;

import api.exam.io.read.info.domain.member.dto.ModifiedMemberRequest;
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

import static api.exam.io.read.info.global.error.ErrorCode.*;

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
            throw new AlreadyJoinedMemberException(ALREADY_JOINED_ACCOUNT);
        } else if(findMember.isPresent() && !findMember.get().isActivated()) {
            memberRepository.delete(findMember.get());
        }

        String encodePass = passwordEncoder.encode(member.getPassword());
        member.setEncodePass(encodePass);

        Member savedMember = memberRepository.save(member);
        return SimpleMemberResponse.of(savedMember);
    }

    public SimpleMemberResponse modified(final ModifiedMemberRequest request, final CustomUserDetails principal) {
        Member modifiedTarget = memberRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND));

        if (modifiedTarget.isActivated() && request.getPassword() != null) {
            String encodePass = passwordEncoder.encode(request.getPassword());
            request.setPassword(encodePass);

            modifiedTarget.modified(request);
        } else if (request.getPassword() == null) {
            request.setPassword(modifiedTarget.getPassword());

            modifiedTarget.modified(request);
        } else throw new MemberNotFoundException(MEMBER_NOT_FOUND);

        return SimpleMemberResponse.of(modifiedTarget);
    }

    public void unActivated(final CustomUserDetails principal) {
        Member findMember = memberRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND));

        if (findMember.isActivated()) {
            findMember.unActivated();
        } else throw new AlreadyPausedMemberException(ALREADY_PAUSED_ACCOUNT);
    }
}
