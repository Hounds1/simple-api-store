package api.exam.io.read.info.domain.category.api;

import api.exam.io.read.info.domain.auth.error.AuthInfoMismatchException;
import api.exam.io.read.info.domain.category.application.CategoryReadService;
import api.exam.io.read.info.domain.category.application.CategoryService;
import api.exam.io.read.info.domain.category.dto.CategoryCreateRequest;
import api.exam.io.read.info.domain.category.dto.SimpleCategoryResponse;
import api.exam.io.read.info.global.security.principal.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public ResponseEntity<SimpleCategoryResponse> create(@RequestBody CategoryCreateRequest request) throws AuthInfoMismatchException {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.create(request.toEntity(),getPrincipal()));
    }

    @DeleteMapping("/categories")
    public ResponseEntity<Void> remove(@RequestParam(name = "target") Long id) {
        categoryService.remove(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/categories")
    public ResponseEntity<SimpleCategoryResponse> findByCategoryName(@RequestParam(name = "categoryName") String categoryName) {
        return ResponseEntity.status(HttpStatus.OK).body(categoryReadService.findByCategoryName(categoryName));
    }
    @GetMapping("/categories/all")
    public ResponseEntity<List<SimpleCategoryResponse>> findAllCategoryByStoreName(@RequestParam(name = "storeName") String storeName) {
        return ResponseEntity.status(HttpStatus.OK).body(categoryReadService.findAllCategoryByStoreName(storeName));
    }

    public CustomUserDetails getPrincipal() {
        return (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
