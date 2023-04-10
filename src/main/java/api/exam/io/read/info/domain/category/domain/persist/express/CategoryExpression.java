package api.exam.io.read.info.domain.category.domain.persist.express;

import api.exam.io.read.info.domain.category.domain.persist.QCategory;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.util.StringUtils;

import static api.exam.io.read.info.domain.category.domain.persist.QCategory.*;

public enum CategoryExpression {
    EQ_NAME {
        @Override
        public BooleanExpression eqCategoryField(String name) {
            if (!StringUtils.hasText(name)) {
                return null;
            }

            return category.name.contains(name);
        }
    },

    EQ_STORE_NAME {
        @Override
        public BooleanExpression eqCategoryField(String storeName) {
            if (!StringUtils.hasText(storeName)) {
                return null;
            }

            return category.storeName.contains(storeName);
        }
    };




    public abstract BooleanExpression eqCategoryField(final String keyword);
}
