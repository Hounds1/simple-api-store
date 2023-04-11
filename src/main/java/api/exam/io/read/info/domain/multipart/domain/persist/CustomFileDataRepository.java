package api.exam.io.read.info.domain.multipart.domain.persist;

import api.exam.io.read.info.domain.multipart.dto.FileSearch;

import java.util.Optional;

public interface CustomFileDataRepository {
    Optional<FileSearch> findByProductId(final Long productId);
}
