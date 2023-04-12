package api.exam.io.read.info.domain.auth.error;

import api.exam.io.read.info.global.error.ErrorCode;
import api.exam.io.read.info.global.error.exception.BusinessException;

public class AuthInfoMismatchException extends BusinessException {
    public AuthInfoMismatchException(ErrorCode errorCode) {
        super(errorCode);
    }
}
