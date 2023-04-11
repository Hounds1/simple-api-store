package api.exam.io.read.info.domain.multipart.application;

import api.exam.io.read.info.domain.multipart.domain.persist.FileDataRepository;
import api.exam.io.read.info.domain.multipart.dto.FileSearch;
import api.exam.io.read.info.domain.multipart.dto.SimpleFileResponse;
import api.exam.io.read.info.domain.multipart.error.FileDataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FileReadService {

    private final FileDataRepository fileDataRepository;

    public SimpleFileResponse output(final Long productId) throws IOException {
        FileSearch fileSearch = fileDataRepository.findByProductId(productId)
                .orElseThrow(() -> new FileDataNotFoundException("파일의 메타데이터가 존재하지 않습니다."));

        return SimpleFileResponse.of(fileSearch);
    }
}
