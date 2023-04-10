package api.exam.io.read.info.domain.category.domain.persist;

import api.exam.io.read.info.domain.category.dto.SimpleCategoryResponse;

import java.util.List;
import java.util.Optional;

public interface CustomCategoryRepository {

    Optional<SimpleCategoryResponse> findByCategoryName(final String categoryName);

    List<SimpleCategoryResponse> findAllCategoryByStoreName(final String storeName);
}
