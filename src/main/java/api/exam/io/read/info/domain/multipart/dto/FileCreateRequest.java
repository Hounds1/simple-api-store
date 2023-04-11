package api.exam.io.read.info.domain.multipart.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class FileCreateRequest {

    private MultipartFile file;

    private Long productId;
}
