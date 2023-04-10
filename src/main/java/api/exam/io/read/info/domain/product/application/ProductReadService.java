package api.exam.io.read.info.domain.product.application;

import api.exam.io.read.info.domain.product.domain.persist.ProductRepository;
import api.exam.io.read.info.domain.product.dto.ProductAllResponse;
import api.exam.io.read.info.domain.product.dto.ProductSearch;
import api.exam.io.read.info.global.common.PageCustomResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ProductReadService {

    private final ProductRepository productRepository;

    public PageCustomResponse<ProductAllResponse> findAllByNameOrStoreName(final ProductSearch productSearch, final Pageable pageable) {
        return productRepository.findProductsByName(productSearch, pageable);
    }
}
