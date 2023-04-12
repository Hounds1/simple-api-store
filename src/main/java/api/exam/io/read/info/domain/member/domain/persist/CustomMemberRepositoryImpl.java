package api.exam.io.read.info.domain.member.domain.persist;

import api.exam.io.read.info.global.security.principal.CustomUserDetails;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static api.exam.io.read.info.domain.member.domain.persist.QMember.*;

@Repository
@RequiredArgsConstructor
public class CustomMemberRepositoryImpl implements CustomMemberRepository{

    private final JPAQueryFactory query;

    @Override
    public Optional<CustomUserDetails> findDetailsByUsername(String username) {

        return Optional.ofNullable(query.select(Projections.constructor(CustomUserDetails.class,
                member.id.as("id"),
                member.username.as("username"),
                member.role.as("role")))
                .from(member)
                .where(member.username.eq(username))
                .fetchOne());
    }
}
