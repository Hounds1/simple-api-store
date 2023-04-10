package api.exam.io.read.info.domain.product.api;

import api.exam.io.read.info.domain.product.application.ProductService;
import api.exam.io.read.info.domain.product.dto.ProductCreateRequest;
import api.exam.io.read.info.domain.product.dto.SimpleProductResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/products")
    public ResponseEntity<SimpleProductResponse> create(@RequestBody final ProductCreateRequest request) {
        log.info("request info : [name : {}], [price : {}], [storeName : {}], [categoryName : {}]",
                request.getName(), request.getPrice(), request.getStoreName(), request.getCategoryName());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productService.create(request.toEntity(), request.getCategoryName()));
    }


}
