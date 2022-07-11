package com.kakao.insurance.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

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

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ExceptionResult> handler(HttpServletRequest request, Exception error) {
        log.error("Unknown Error : {}", error.getMessage());

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
