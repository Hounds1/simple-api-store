package api.exam.io.read.info.domain.product.error;

import api.exam.io.read.info.global.error.ErrorCode;
import api.exam.io.read.info.global.error.exception.BusinessException;

public class ProductNotFoundException extends BusinessException {
    public ProductNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
