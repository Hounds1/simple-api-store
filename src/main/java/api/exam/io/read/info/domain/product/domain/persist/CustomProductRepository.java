package api.exam.io.read.info.domain.product.domain.persist;

import api.exam.io.read.info.domain.product.dto.ProductAllResponse;
import api.exam.io.read.info.domain.product.dto.ProductSearch;
import api.exam.io.read.info.global.common.PageCustomResponse;
import org.springframework.data.domain.Pageable;

public interface CustomProductRepository {

    PageCustomResponse<ProductAllResponse> findProductsByName(final ProductSearch productSearch, final Pageable pageable);
}
