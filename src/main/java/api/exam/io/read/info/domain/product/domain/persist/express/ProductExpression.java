package api.exam.io.read.info.domain.product.domain.persist.express;

import api.exam.io.read.info.domain.product.domain.persist.QProduct;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.util.StringUtils;

import static api.exam.io.read.info.domain.product.domain.persist.QProduct.*;

public enum ProductExpression {
    EQ_NAME{
        @Override
        public BooleanExpression eqProductField(String name) {
            if (!StringUtils.hasText(name)) {
                return null;
            }

            return product.name.contains(name);
        }
    },

    EQ_STORE_NAME{
        @Override
        public BooleanExpression eqProductField(String storeName) {
            if (!StringUtils.hasText(storeName)) {
                return null;
            }

            return product.storeName.contains(storeName);
        }
    };

    public abstract BooleanExpression eqProductField(final String keyword);
}
