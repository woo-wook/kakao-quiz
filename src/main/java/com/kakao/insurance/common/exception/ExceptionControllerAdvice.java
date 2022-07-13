package com.kakao.insurance.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

import java.util.Objects;

import static com.kakao.insurance.common.exception.ExceptionCode.BAD_REQUEST;
import static com.kakao.insurance.common.exception.ExceptionCode.UNKNOWN_ERROR;

@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice {
    @ExceptionHandler(value = UncheckedException.class)
    public ResponseEntity<ExceptionResult> handler(HttpServletRequest request, UncheckedException error) {
        return ResponseEntity
                .status(error.getError().getStatus())
                .body(
                        ExceptionResult.builder()
                                .code(error.getError().getCode())
                                .message(error.getError().getMessage())
                                .build()
                );
    }

    @ExceptionHandler(value = BindException.class)
    public ResponseEntity<ExceptionResult> handler(HttpServletRequest request, BindException error) {
        String message = "";

        if(error.getBindingResult().hasErrors()) {
            FieldError fieldError = Objects.requireNonNull(error.getBindingResult().getFieldError());
            message = "[Parameter: " + fieldError.getField() + "] " + fieldError.getDefaultMessage();
        }

        return ResponseEntity
                .status(BAD_REQUEST.getStatus())
                .body(
                        ExceptionResult.builder()
                                .code(BAD_REQUEST.getCode())
                                .message(message)
                                .build()
                );
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ExceptionResult> handler(HttpServletRequest request, Exception error) {
        log.error("Unknown Error : {}", error.getMessage());
        error.printStackTrace();

        return ResponseEntity
                .status(UNKNOWN_ERROR.getStatus())
                .body(
                        ExceptionResult.builder()
                                .code(UNKNOWN_ERROR.getCode())
                                .message(UNKNOWN_ERROR.getMessage())
                                .build()
                );
    }
}
