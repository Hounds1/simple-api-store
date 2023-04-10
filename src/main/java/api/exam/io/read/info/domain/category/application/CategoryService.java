package api.exam.io.read.info.domain.category.application;

import api.exam.io.read.info.domain.category.domain.persist.Category;
import api.exam.io.read.info.domain.category.domain.persist.CategoryRepository;
import api.exam.io.read.info.domain.category.dto.SimpleCategoryResponse;
import api.exam.io.read.info.domain.category.error.CategoryNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public SimpleCategoryResponse create(final Category category) {
        Category savedCategory = categoryRepository.save(category);

        return SimpleCategoryResponse.of(savedCategory);
    }

    public void remove(final Long id) {
        Category findCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("찾을 수 없는 상태입니다."));

        categoryRepository.delete(findCategory);
    }
}
