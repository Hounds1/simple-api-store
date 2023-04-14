package api.exam.io.read.info.domain.multipart.application;

import api.exam.io.read.info.domain.multipart.domain.persist.FileDataRepository;
import api.exam.io.read.info.domain.multipart.dto.FileSearch;
import api.exam.io.read.info.domain.multipart.dto.SimpleFileResponse;
import api.exam.io.read.info.domain.multipart.error.FileDataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

import static api.exam.io.read.info.global.error.ErrorCode.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FileReadService {

    private final FileDataRepository fileDataRepository;

    public SimpleFileResponse output(final Long productId) throws IOException {
        FileSearch fileSearch = fileDataRepository.findByProductId(productId)
                .orElseThrow(() -> new FileDataNotFoundException(DATA_NOT_FOUND));

        return SimpleFileResponse.of(fileSearch);
    }
}
