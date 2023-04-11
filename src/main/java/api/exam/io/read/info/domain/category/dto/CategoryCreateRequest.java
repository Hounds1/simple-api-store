package api.exam.io.read.info.domain.category.dto;

import api.exam.io.read.info.domain.category.domain.persist.Category;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class CategoryCreateRequest {

    private String name;

    private String storeName;

    public Category toEntity() {
        return Category.builder()
                .name(name)
                .storeName(storeName)
                .build();
    }
}
