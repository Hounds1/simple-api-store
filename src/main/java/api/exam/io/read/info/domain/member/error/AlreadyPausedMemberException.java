package api.exam.io.read.info.domain.member.error;

import api.exam.io.read.info.global.error.ErrorCode;
import api.exam.io.read.info.global.error.exception.BusinessException;

public class AlreadyPausedMemberException extends BusinessException {
    public AlreadyPausedMemberException(ErrorCode errorCode) {
        super(errorCode);
    }
}
