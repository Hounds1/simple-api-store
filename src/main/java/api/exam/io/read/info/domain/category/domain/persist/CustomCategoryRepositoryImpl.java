package api.exam.io.read.info.domain.category.domain.persist;

import api.exam.io.read.info.domain.category.dto.SimpleCategoryResponse;
import api.exam.io.read.info.domain.category.error.NoSearchCategories;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static api.exam.io.read.info.domain.category.domain.persist.QCategory.*;


@Repository
@RequiredArgsConstructor
public class CustomCategoryRepositoryImpl implements CustomCategoryRepository {

    private final JPAQueryFactory query;

    @Override
    public Optional<SimpleCategoryResponse> findByCategoryName(final String categoryName) {
        return Optional.ofNullable(query.select(Projections.constructor(SimpleCategoryResponse.class,
                        category.id,
                        category.name,
                        category.storeName))
                .from(category)
                .where(category.name.eq(categoryName))
                .fetchOne());
    }

    @Override
    public List<SimpleCategoryResponse> findAllCategoryByStoreName(final String storeName) {
        List<SimpleCategoryResponse> categories = query.select(Projections.constructor(SimpleCategoryResponse.class,
                        category.id,
                        category.name,
                        category.storeName))
                .from(category)
                .where(category.storeName.eq(storeName))
                .fetch();

        if (categories.size() == 0) {
            throw new NoSearchCategories("카테고리가 존재하지 않습니다.");
        }

        return categories;
    }
}
