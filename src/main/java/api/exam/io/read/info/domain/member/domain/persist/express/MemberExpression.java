package api.exam.io.read.info.domain.member.domain.persist.express;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.util.StringUtils;

import static api.exam.io.read.info.domain.member.domain.persist.QMember.*;

public enum MemberExpression {
    EQ_USERNAME {
        @Override
        public BooleanExpression eqMemberField(String username) {
            if (!StringUtils.hasText(username)) {
                return null;
            }

            return member.username.contains(username);
        }
    },
    EQ_STORE_NAME {
        @Override
        public BooleanExpression eqMemberField(String storeName) {
            if (!StringUtils.hasText(storeName)) {
                return null;
            }

            return member.storeName.contains(storeName);
        }
    };

    public abstract BooleanExpression eqMemberField(final String keyword);
}
