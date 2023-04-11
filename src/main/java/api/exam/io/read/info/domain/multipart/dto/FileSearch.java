package api.exam.io.read.info.domain.multipart.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class FileSearch {

    private String filePath;

    private String contentType;
}
