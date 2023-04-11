package api.exam.io.read.info.domain.multipart.application;

import api.exam.io.read.info.domain.multipart.domain.persist.FileData;
import api.exam.io.read.info.domain.multipart.domain.persist.FileDataRepository;
import api.exam.io.read.info.domain.multipart.dto.FileCreateRequest;
import api.exam.io.read.info.domain.multipart.dto.FileSearch;
import api.exam.io.read.info.domain.multipart.dto.SimpleFileResponse;
import api.exam.io.read.info.domain.multipart.error.CanNotSaveFileDataException;
import api.exam.io.read.info.domain.multipart.error.FileDataNotFoundException;
import api.exam.io.read.info.domain.product.domain.persist.Product;
import api.exam.io.read.info.domain.product.domain.persist.ProductRepository;
import api.exam.io.read.info.domain.product.error.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class FileService {

    private static final String LOCAL_PATH = "C:\\Users\\Student\\Desktop\\study\\imgs\\";
    private final FileDataRepository fileDataRepository;
    private final ProductRepository productRepository;

    public void create(final FileCreateRequest request) throws CanNotSaveFileDataException {
        String newFileName = getNewFileName();

        FileData fileData = FileData.builder()
                .fileName(newFileName)
                .contentType(request.getFile().getContentType())
                .filePath(LOCAL_PATH + newFileName)
                .build();

        try {
            request.getFile().transferTo(new File(LOCAL_PATH + newFileName));
        } catch (Exception e) {
            throw new CanNotSaveFileDataException("세이브 할 수 없는 상태");
        }

        FileData savedFileData = fileDataRepository.save(fileData);

        Product findProduct = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ProductNotFoundException("찾을 수 없는 상태의 상품입니다."));

        findProduct.setFileData(savedFileData);
    }


    public String getNewFileName() {
        return UUID.randomUUID().toString();
    }
}
