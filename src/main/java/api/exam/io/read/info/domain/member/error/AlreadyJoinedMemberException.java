package api.exam.io.read.info.domain.member.error;

import api.exam.io.read.info.global.error.ErrorCode;
import api.exam.io.read.info.global.error.exception.BusinessException;

public class AlreadyJoinedMemberException extends BusinessException {
    public AlreadyJoinedMemberException(ErrorCode errorCode) {
        super(errorCode);
    }
}
