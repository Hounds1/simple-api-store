package api.exam.io.read.info.domain.product.domain.persist;

import api.exam.io.read.info.domain.category.domain.persist.QCategory;
import api.exam.io.read.info.domain.product.domain.persist.express.ProductExpression;
import api.exam.io.read.info.domain.product.dto.ProductAllResponse;
import api.exam.io.read.info.domain.product.dto.ProductSearch;
import api.exam.io.read.info.global.common.PageCustomResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static api.exam.io.read.info.domain.category.domain.persist.QCategory.*;
import static api.exam.io.read.info.domain.product.domain.persist.QProduct.*;
import static api.exam.io.read.info.domain.product.domain.persist.express.ProductExpression.*;

@Repository
@RequiredArgsConstructor
public class CustomProductRepositoryImpl implements CustomProductRepository{

    private final JPAQueryFactory query;

    @Override
    public PageCustomResponse<ProductAllResponse> findProductsByName(final ProductSearch productSearch, final Pageable pageable) {
        List<ProductAllResponse> products = query.select(Projections.constructor(ProductAllResponse.class,
                        product.name,
                        product.price,
                        product.storeName,
                        product.category.name))
                .from(product)
                .leftJoin(product.category, category)
                .where(EQ_NAME.eqProductField(productSearch.getName()),
                        EQ_STORE_NAME.eqProductField(productSearch.getStoreName()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        if (products.size() == 0) {
            return PageCustomResponse.of(Page.empty());
        }

        JPAQuery<Long> count = query.select(product.count())
                .from(product);

        return PageCustomResponse.of(PageableExecutionUtils.getPage(products, pageable, count::fetchFirst));
    }
}
