package api.exam.io.read.info.domain.multipart.api;

import api.exam.io.read.info.domain.multipart.application.FileReadService;
import api.exam.io.read.info.domain.multipart.application.FileService;
import api.exam.io.read.info.domain.multipart.dto.FileCreateRequest;
import api.exam.io.read.info.domain.multipart.dto.SimpleFileResponse;
import api.exam.io.read.info.domain.multipart.error.CanNotSaveFileDataException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class FileController {

    private final FileService fileService;
    private final FileReadService fileReadService;
    @PostMapping("/files")
    public ResponseEntity<Void> create(@RequestParam(name = "file")MultipartFile file,
                                       @RequestParam(name = "productId")Long productId) throws CanNotSaveFileDataException {
        FileCreateRequest request = FileCreateRequest.builder()
                .file(file)
                .productId(productId)
                .build();

        fileService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/files")
    public ResponseEntity<byte[]> output(@RequestParam(name = "productId") Long productId) throws IOException {
        SimpleFileResponse output = fileReadService.output(productId);
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(output.getContentType()))
                .body(output.getFile());
    }
}
