package api.exam.io.read.info.domain.product.dto;

import api.exam.io.read.info.domain.product.domain.persist.Product;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class SimpleProductResponse {

    private String name;
    private int price;

    public static SimpleProductResponse of(final Product product) {
        return new SimpleProductResponse(product.getName(), product.getPrice());
    }
}
