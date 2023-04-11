package api.exam.io.read.info.domain.product.api;

import api.exam.io.read.info.domain.product.application.ProductReadService;
import api.exam.io.read.info.domain.product.application.ProductService;
import api.exam.io.read.info.domain.product.dto.ProductAllResponse;
import api.exam.io.read.info.domain.product.dto.ProductCreateRequest;
import api.exam.io.read.info.domain.product.dto.ProductSearch;
import api.exam.io.read.info.domain.product.dto.SimpleProductResponse;
import api.exam.io.read.info.global.common.PageCustomResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1")
public class ProductController {

    private final ProductService productService;
    private final ProductReadService productReadService;

    @PostMapping("/products")
    public ResponseEntity<SimpleProductResponse> create(@RequestBody ProductCreateRequest request) {
        log.info("create product request info : [name : {}], [price : {}], [storeName : {}], [categoryName : {}]",
                request.getName(), request.getPrice(), request.getStoreName(), request.getCategoryName());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productService.create(request.toEntity(), request.getCategoryName()));
    }

    @DeleteMapping("/products")
    public ResponseEntity<Void> remove(@RequestParam(name = "name") String name) {
        productService.remove(name);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/products/all")
    public ResponseEntity<PageCustomResponse<ProductAllResponse>> findAllByNameOrStoreName(
            @ModelAttribute ProductSearch productSearch,
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC)Pageable pageable) {

        log.info("findAllByName request info : [name : {}], [storeName : {}] ,[page : {}]"
                , productSearch.getName(), productSearch.getStoreName() , pageable.getPageNumber());

        return ResponseEntity.status(HttpStatus.OK)
                .body(productReadService.findAllByNameOrStoreName(productSearch, pageable));
    }
}
