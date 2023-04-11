package api.exam.io.read.info.domain.multipart.domain.persist;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FileDataRepository extends JpaRepository<FileData,Long>, CustomFileDataRepository {
}
