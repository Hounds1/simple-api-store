package api.exam.io.read.info.domain.member.domain.persist;

import api.exam.io.read.info.domain.member.dto.SimpleMemberResponse;
import api.exam.io.read.info.global.common.PageCustomResponse;
import api.exam.io.read.info.global.security.principal.CustomUserDetails;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CustomMemberRepository {

    Optional<CustomUserDetails> findDetailsByUsername(final String username);

    PageCustomResponse<SimpleMemberResponse> findAllByUsername(final String username, final Pageable pageable);

    PageCustomResponse<SimpleMemberResponse> findAllByStoreName(final String storeName, final Pageable pageable);
}
