package api.exam.io.read.info.global.error;

import api.exam.io.read.info.global.error.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static api.exam.io.read.info.global.error.ErrorCode.*;

@Slf4j
@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {

        ErrorCode errorCode = e.getErrorCode();
        ErrorResponse errorResponse = new ErrorResponse(errorCode);

        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(errorResponse.getStatus()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException : [{}]", e);
        ErrorResponse errorResponse = ErrorResponse.createBinding(INVALID_INPUT_VALUE, e.getBindingResult());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
