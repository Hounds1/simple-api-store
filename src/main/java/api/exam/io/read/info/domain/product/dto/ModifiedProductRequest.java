package api.exam.io.read.info.domain.product.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ModifiedProductRequest {

    private Long productId;

    private String name;

    private Long categoryId;

    private int price;
}
