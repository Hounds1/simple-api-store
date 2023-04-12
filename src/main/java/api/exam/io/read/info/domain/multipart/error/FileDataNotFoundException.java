package api.exam.io.read.info.domain.multipart.error;

import api.exam.io.read.info.global.error.ErrorCode;
import api.exam.io.read.info.global.error.exception.BusinessException;

public class FileDataNotFoundException extends BusinessException {
    public FileDataNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
