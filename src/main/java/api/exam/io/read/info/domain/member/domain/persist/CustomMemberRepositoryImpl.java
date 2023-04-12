package api.exam.io.read.info.domain.member.domain.persist;

import api.exam.io.read.info.domain.member.dto.SimpleMemberResponse;
import api.exam.io.read.info.global.common.PageCustomResponse;
import api.exam.io.read.info.global.security.principal.CustomUserDetails;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static api.exam.io.read.info.domain.member.domain.persist.QMember.*;
import static api.exam.io.read.info.domain.member.domain.persist.express.MemberExpression.*;

@Repository
@RequiredArgsConstructor
public class CustomMemberRepositoryImpl implements CustomMemberRepository{

    private final JPAQueryFactory query;

    @Override
    public Optional<CustomUserDetails> findDetailsByUsername(final String username) {

        return Optional.ofNullable(query.select(Projections.constructor(CustomUserDetails.class,
                member.id.as("id"),
                member.username.as("username"),
                member.role.as("role")))
                .from(member)
                .where(member.username.eq(username))
                .fetchOne());
    }

    @Override
    public PageCustomResponse<SimpleMemberResponse> findAllByUsername(final String username, final Pageable pageable) {
        List<SimpleMemberResponse> members
                = query.select(Projections.constructor(SimpleMemberResponse.class,
                        member.username.as("username"),
                        member.storeName.as("storeName"),
                        member.role.as("role")))
                .from(member)
                .where(EQ_USERNAME.eqMemberField(username))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        if (members.size() == 0) {
            return PageCustomResponse.of(Page.empty());
        }

        JPAQuery<Long> count = query.select(member.count())
                .from(member)
                .where(EQ_USERNAME.eqMemberField(username));

        return PageCustomResponse.of(PageableExecutionUtils.getPage(members, pageable, count::fetchFirst));
    }

    @Override
    public PageCustomResponse<SimpleMemberResponse> findAllByStoreName(final String storeName, final Pageable pageable) {
        List<SimpleMemberResponse> members = query.select(Projections.constructor(SimpleMemberResponse.class,
                        member.username.as("username"),
                        member.storeName.as("storeName"),
                        member.role.as("role")))
                .from(member)
                .where(EQ_STORE_NAME.eqMemberField(storeName))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        if (members.size() == 0) {
            return PageCustomResponse.of(Page.empty());
        }

        JPAQuery<Long> count = query.select(member.count())
                .from(member)
                .where(EQ_STORE_NAME.eqMemberField(storeName));

        return PageCustomResponse.of(PageableExecutionUtils.getPage(members, pageable, count::fetchFirst));
    }


}
