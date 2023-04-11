package api.exam.io.read.info.domain.product.domain.persist;

import api.exam.io.read.info.domain.category.domain.persist.Category;
import api.exam.io.read.info.domain.multipart.domain.persist.FileData;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Product {

    @Id @GeneratedValue
    private Long id;

    private String name;

    private int price;

    private String storeName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_data_id")
    private FileData fileData;

    /**
     * 비즈니스 로직
     */

    public void setCategory(final Category category) {
        this.category = category;
    }

    public void setFileData(final FileData fileData) {
        this.fileData = fileData;
    }
}
