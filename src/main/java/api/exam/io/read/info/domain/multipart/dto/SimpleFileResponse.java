package api.exam.io.read.info.domain.multipart.dto;

import api.exam.io.read.info.domain.multipart.domain.persist.FileData;
import lombok.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class SimpleFileResponse {

    private byte[] file;

    private String contentType;

    public static SimpleFileResponse of(final FileSearch fileData) throws IOException {
        byte[] file = Files.readAllBytes(new File(fileData.getFilePath()).toPath());
        return new SimpleFileResponse(file, fileData.getContentType());
    }
}
