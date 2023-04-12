package api.exam.io.read.info.domain.category.application;

import api.exam.io.read.info.domain.category.domain.persist.CategoryRepository;
import api.exam.io.read.info.domain.category.dto.SimpleCategoryResponse;
import api.exam.io.read.info.domain.category.error.CategoryNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static api.exam.io.read.info.global.error.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class CategoryReadService {

    private final CategoryRepository categoryRepository;

    public SimpleCategoryResponse findByCategoryName(final String categoryName) {
        return categoryRepository.findByCategoryName(categoryName)
                .orElseThrow(() -> new CategoryNotFoundException(CATEGORY_NOT_FOUND));
    }

    public List<SimpleCategoryResponse> findAllCategoryByStoreName(final String storeName) {
        return categoryRepository.findAllCategoryByStoreName(storeName);
    }
}
