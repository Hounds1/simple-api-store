package api.exam.io.read.info.domain.category.dto;

import api.exam.io.read.info.domain.category.domain.persist.Category;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class SimpleCategoryResponse {

    private Long id;

    private String name;

    private String storeName;

    public static SimpleCategoryResponse of(final Category category) {
        return SimpleCategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .storeName(category.getStoreName())
                .build();
    }
}
