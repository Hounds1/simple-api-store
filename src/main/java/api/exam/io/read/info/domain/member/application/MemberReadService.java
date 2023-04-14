package api.exam.io.read.info.domain.member.application;

import api.exam.io.read.info.domain.member.domain.persist.MemberRepository;
import api.exam.io.read.info.domain.member.dto.SimpleMemberResponse;
import api.exam.io.read.info.global.common.PageCustomResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class MemberReadService {

    private final MemberRepository memberRepository;

    public PageCustomResponse<SimpleMemberResponse> findAllByUsername(final String username, final Pageable pageable) {
        return memberRepository.findAllByUsername(username, pageable);
    }

    public PageCustomResponse<SimpleMemberResponse> findAllByStoreName(final String storeName, final Pageable pageable) {
        return memberRepository.findAllByStoreName(storeName, pageable);
    }
}
