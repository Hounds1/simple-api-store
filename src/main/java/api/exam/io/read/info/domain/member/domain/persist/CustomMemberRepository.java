package api.exam.io.read.info.domain.member.domain.persist;

import api.exam.io.read.info.global.security.principal.CustomUserDetails;

import java.util.Optional;

public interface CustomMemberRepository {

    Optional<CustomUserDetails> findDetailsByUsername(final String username);
}
