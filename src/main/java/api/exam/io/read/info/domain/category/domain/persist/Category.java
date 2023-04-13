package api.exam.io.read.info.domain.category.domain.persist;

import api.exam.io.read.info.domain.category.dto.ModifiedCategoryRequest;
import api.exam.io.read.info.domain.product.domain.persist.Product;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Category {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String storeName;

    @OneToMany(mappedBy = "category")
    List<Product> products = new ArrayList<>();

    public void modified(final ModifiedCategoryRequest request) {
        this.name = request.getName();
    }
}
