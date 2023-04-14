package api.exam.io.read.info.domain.category.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ModifiedCategoryRequest {

    private Long categoryId;
    private String name;
}
