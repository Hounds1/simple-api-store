package api.exam.io.read.info.domain.category.api;

import api.exam.io.read.info.domain.category.application.CategoryReadService;
import api.exam.io.read.info.domain.category.application.CategoryService;
import api.exam.io.read.info.domain.category.dto.CategoryCreateRequest;
import api.exam.io.read.info.domain.category.dto.SimpleCategoryResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1")
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryReadService categoryReadService;

    @PostMapping("/categories")
    public ResponseEntity<SimpleCategoryResponse> create(@RequestBody final CategoryCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.create(request.toEntity()));
    }

    @DeleteMapping("/categories")
    public ResponseEntity<Void> remove(@RequestParam(name = "target") final Long id) {
        categoryService.remove(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/categories")
    public ResponseEntity<SimpleCategoryResponse> findByCategoryName(@RequestParam(name = "categoryName") final String categoryName) {
        return ResponseEntity.status(HttpStatus.OK).body(categoryReadService.findByCategoryName(categoryName));
    }

    @GetMapping("/categories/all")
    public ResponseEntity<List<SimpleCategoryResponse>> findAllCategoryByStoreName(@RequestParam(name = "storeName") final String storeName) {
        return ResponseEntity.status(HttpStatus.OK).body(categoryReadService.findAllCategoryByStoreName(storeName));
    }
}
