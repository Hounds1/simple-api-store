package api.exam.io.read.info.domain.multipart.domain.persist;

import api.exam.io.read.info.domain.product.domain.persist.Product;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class FileData {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    private String filePath;

    private String contentType;

    @OneToOne(mappedBy = "fileData")
    private Product product;
}
