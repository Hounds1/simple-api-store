package api.exam.io.read.info.domain.multipart.domain.persist;

import api.exam.io.read.info.domain.multipart.dto.FileSearch;
import api.exam.io.read.info.domain.multipart.dto.SimpleFileResponse;
import api.exam.io.read.info.domain.product.domain.persist.QProduct;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static api.exam.io.read.info.domain.multipart.domain.persist.QFileData.*;
import static api.exam.io.read.info.domain.product.domain.persist.QProduct.*;

@Repository
@RequiredArgsConstructor
public class CustomFileDataRepositoryImpl implements CustomFileDataRepository{

    private final JPAQueryFactory query;

    @Override
    public Optional<FileSearch> findByProductId(Long productId) {
        return Optional.ofNullable(query.select(Projections.constructor(FileSearch.class,
                fileData.filePath,
                fileData.contentType))
                .from(fileData)
                        .leftJoin(fileData.product, product)
                .where(fileData.product.id.eq(productId))
                .fetchOne());
    }
}
