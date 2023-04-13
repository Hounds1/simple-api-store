package api.exam.io.read.info.domain.product.application;

import api.exam.io.read.info.domain.category.domain.persist.Category;
import api.exam.io.read.info.domain.category.domain.persist.CategoryRepository;
import api.exam.io.read.info.domain.category.error.CategoryNotFoundException;
import api.exam.io.read.info.domain.product.domain.persist.Product;
import api.exam.io.read.info.domain.product.domain.persist.ProductRepository;
import api.exam.io.read.info.domain.product.dto.ModifiedProductRequest;
import api.exam.io.read.info.domain.product.dto.SimpleProductResponse;
import api.exam.io.read.info.domain.product.error.ProductNotFoundException;
import api.exam.io.read.info.domain.product.error.StoreNameMismatchException;
import api.exam.io.read.info.global.security.principal.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static api.exam.io.read.info.global.error.ErrorCode.*;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public SimpleProductResponse create(final Product product, final String categoryName) {
        Category findCategory = categoryRepository.findByName(categoryName)
                .orElseThrow(() -> new CategoryNotFoundException(CATEGORY_NOT_FOUND));

        log.info("findCategory name is [{}]", findCategory.getName());

        product.setCategory(findCategory);
        Product savedProduct = productRepository.save(product);

        return SimpleProductResponse.of(savedProduct);
    }

    public SimpleProductResponse modified(final ModifiedProductRequest request, final CustomUserDetails principal) {
        Product findProduct = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND));

        if (!findProduct.getStoreName().equals(principal.getStoreName())) {
            throw new StoreNameMismatchException(STORE_NAME_MISMATCH);
        }

        if (!Objects.equals(findProduct.getCategory().getId(), request.getProductId())) {
            Category newCategory = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new CategoryNotFoundException(CATEGORY_NOT_FOUND));
            findProduct.setCategory(newCategory);
        }

        findProduct.modified(request);

        return SimpleProductResponse.of(findProduct);
    }

    public void remove(final String name) {
        Product findProduct = productRepository.findByName(name)
                .orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND));

        productRepository.delete(findProduct);
    }
}
