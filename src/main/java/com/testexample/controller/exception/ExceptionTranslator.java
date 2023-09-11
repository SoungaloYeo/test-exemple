package com.testexample.controller.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class ExceptionTranslator extends ResponseEntityExceptionHandler {


    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleDataIntegrityViolationException(DataIntegrityViolationException ex, NativeWebRequest request) {

        HttpServletRequest nativeRequest = request.getNativeRequest(HttpServletRequest.class);
        String requestUri = nativeRequest != null ? nativeRequest.getRequestURI() : StringUtils.EMPTY;

        var apiError = ApiError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(ex.getLocalizedMessage())
                .path(requestUri).build();
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }


    @Override
    public  ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode
        status, WebRequest request) {
        var errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(c -> FieldErrorVM.builder()
                        .message(c.getDefaultMessage())
                        .field(c.getField())
                        .objectName(c.getClass().getName()).build())
                .collect(Collectors.toList());

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, "");
        problemDetail.setTitle("error");
        problemDetail.setProperty("errors", errors);

        return new ResponseEntity<>(problemDetail, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        var errors = ex.getSupportedHttpMethods()
                .stream()
                .map(method -> FieldErrorVM.builder()
                        .objectName(method.getClass().getName())
                        .build())
                .collect(Collectors.toList());

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, "");
        problemDetail.setTitle("");
        problemDetail.setProperty("errors", errors);

        return new ResponseEntity<>(problemDetail, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler
    public ResponseEntity<Object> handleAlreadyExistsException(AlreadyExistsException ex, NativeWebRequest request) {

        var nativeRequest = request.getNativeRequest(HttpServletRequest.class);
        String requestUri = nativeRequest != null ? nativeRequest.getRequestURI() : StringUtils.EMPTY;

        var apiError = ApiError.builder()
                .status(HttpStatus.CONFLICT)
                .message(ex.getMessage())
                .path(requestUri)
                .build();
        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiError> handleRestClientException(EntityNotFoundException ex, NativeWebRequest request) {

        HttpServletRequest nativeRequest = request.getNativeRequest(HttpServletRequest.class);
        String requestUri = nativeRequest != null ? nativeRequest.getRequestURI() : StringUtils.EMPTY;

        ApiError apiError = ApiError.builder()
                .status(HttpStatus.NOT_FOUND)
                .message("Entity not found")
                .path(requestUri).build();
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

}
