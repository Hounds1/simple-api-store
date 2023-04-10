package api.exam.io.read.info.domain.category.application;

import api.exam.io.read.info.domain.category.domain.persist.CategoryRepository;
import api.exam.io.read.info.domain.category.dto.SimpleCategoryResponse;
import api.exam.io.read.info.domain.category.error.CategoryNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class CategoryReadService {

    private final CategoryRepository categoryRepository;

    public SimpleCategoryResponse findByCategoryName(final String categoryName) {
        return categoryRepository.findByCategoryName(categoryName)
                .orElseThrow(() -> new CategoryNotFoundException("찾을 수 있는 상태가 아닙니다."));
    }
}
