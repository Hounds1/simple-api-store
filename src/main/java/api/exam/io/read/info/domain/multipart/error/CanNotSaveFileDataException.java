package api.exam.io.read.info.domain.multipart.error;

import api.exam.io.read.info.global.error.ErrorCode;
import api.exam.io.read.info.global.error.exception.BusinessException;

import java.io.IOException;

public class CanNotSaveFileDataException extends BusinessException {
    public CanNotSaveFileDataException(ErrorCode errorCode) {
        super(errorCode);
    }
}
