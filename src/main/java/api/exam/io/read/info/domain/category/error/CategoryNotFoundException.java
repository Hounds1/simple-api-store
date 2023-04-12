package api.exam.io.read.info.domain.category.error;

import api.exam.io.read.info.global.error.ErrorCode;
import api.exam.io.read.info.global.error.exception.BusinessException;

public class CategoryNotFoundException extends BusinessException {
    public CategoryNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
