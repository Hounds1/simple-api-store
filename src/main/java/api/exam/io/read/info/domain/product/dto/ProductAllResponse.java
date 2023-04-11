package api.exam.io.read.info.domain.product.dto;

import api.exam.io.read.info.domain.product.domain.persist.Product;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ProductAllResponse {

    private String name;

    private int price;

    private String StoreName;

    private String categoryName;

    public static ProductAllResponse of(final Product product) {
        return new ProductAllResponse(product.getName(), product.getPrice(), product.getStoreName(), product.getCategory().getName());
    }
}
