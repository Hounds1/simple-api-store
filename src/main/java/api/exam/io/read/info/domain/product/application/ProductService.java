package api.exam.io.read.info.domain.product.application;

import api.exam.io.read.info.domain.category.domain.persist.Category;
import api.exam.io.read.info.domain.category.domain.persist.CategoryRepository;
import api.exam.io.read.info.domain.category.error.CategoryNotFoundException;
import api.exam.io.read.info.domain.product.domain.persist.Product;
import api.exam.io.read.info.domain.product.domain.persist.ProductRepository;
import api.exam.io.read.info.domain.product.dto.SimpleProductResponse;
import api.exam.io.read.info.domain.product.error.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public SimpleProductResponse create(final Product product, final String categoryName) {
        Category findCategory = categoryRepository.findByName(categoryName)
                .orElseThrow(() -> new CategoryNotFoundException("찾을 수 없는 상태입니다."));

        log.info("findCategory name is [{}]", findCategory.getName());

        product.setCategory(findCategory);
        Product savedProduct = productRepository.save(product);

        return SimpleProductResponse.of(savedProduct);
    }

    public void remove(final String name) {
        Product findProduct = productRepository.findByName(name)
                .orElseThrow(() -> new ProductNotFoundException("찾을 수 없는 상태입니다."));

        productRepository.delete(findProduct);
    }
}
