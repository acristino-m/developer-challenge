package com.minsait.challenge.twitter.handler;

import com.minsait.challenge.twitter.domain.BaseResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
@Slf4j
public class TwitterHandler {

    /**
     * This method handles Bad Requests.
     *
     * @param ex the thrown exception
     * @param wr the WebRequest
     */
    @ExceptionHandler({
            HttpMessageNotReadableException.class,
            ServletRequestBindingException.class,
            HttpMediaTypeNotSupportedException.class
    })
    public ResponseEntity<BaseResponseDto> bindingExceptions(Exception ex, WebRequest wr) {
        log.error("Binding failed {}", wr.getDescription(false), ex);
        return buildResponseMessage(HttpStatus.BAD_REQUEST, "Binding failed");
    }

    /**
     * This method handles Validation Exceptions.
     *
     * @return ResponseEntity<?> returns Bad Request
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponseDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest wr) {
        StringBuilder errors = new StringBuilder();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            errors.append(error.getDefaultMessage());
        });
        log.error("Invalid request: {}", errors);
        return buildResponseMessage(HttpStatus.BAD_REQUEST, "Validation failed");
    }

    /**
     * This method handles unknown Exceptions and Server Errors.
     *
     * @param ex the thrown exception
     * @param wr the WebRequest
     */
    @ExceptionHandler({Exception.class})
    public ResponseEntity<BaseResponseDto> handleUnknownException(Exception ex, WebRequest wr) {
        log.error("Unable to handle {}", wr.getDescription(false), ex);
        return buildResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
    }

    public static ResponseEntity<BaseResponseDto> buildResponseMessage(HttpStatus httpStatus, Exception ex) {
        return buildResponseMessage(httpStatus, ex.getMessage());
    }

    public static ResponseEntity<BaseResponseDto> buildResponseMessage(HttpStatus httpStatus) {
        BaseResponseDto response = new BaseResponseDto() {};
        response.setInfo(httpStatus);
        return ResponseEntity.status(httpStatus).body(response);
    }

    public static ResponseEntity<BaseResponseDto> buildResponseMessage(HttpStatus httpStatus, String message) {
        BaseResponseDto response = new BaseResponseDto() {};
        response.setCode(httpStatus.value());
        response.setMessage(message);
        return ResponseEntity.status(httpStatus).body(response);
    }
}
