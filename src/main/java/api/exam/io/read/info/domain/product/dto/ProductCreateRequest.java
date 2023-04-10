package api.exam.io.read.info.domain.product.dto;

import api.exam.io.read.info.domain.product.domain.persist.Product;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ProductCreateRequest {

    private String name;

    private int price;

    private String storeName;

    private String categoryName;

    public Product toEntity() {
        return Product.builder()
                .name(name)
                .price(price)
                .storeName(storeName)
                .build();
    }
}
