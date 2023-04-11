package api.exam.io.read.info.domain.multipart.error;

import java.io.IOException;

public class CanNotSaveFileDataException extends IOException {
    public CanNotSaveFileDataException(String message) {
        super(message);
    }
}
