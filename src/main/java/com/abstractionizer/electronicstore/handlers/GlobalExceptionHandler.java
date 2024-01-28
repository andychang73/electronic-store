package com.abstractionizer.electronicstore.handlers;

import com.abstractionizer.electronicstore.exceptions.BusinessException;
import com.abstractionizer.electronicstore.response.ErrorResp;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.stream.Collectors;

import static com.abstractionizer.electronicstore.errors.Error.*;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ErrorResp handleException(Exception e){
        log.error(e.getMessage(), e);
        return new ErrorResp(INTERNAL_SERVER_ERROR, e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public ErrorResp handleBadRequestException(ConstraintViolationException e){
        log.error(e.getMessage(), e);
        List<String> errors = e.getConstraintViolations().stream()
                .map(c -> String.format("parameter '%S' %s (actual value: %s)", c.getPropertyPath(), c.getMessage(), c.getInvalidValue()))
                .collect(Collectors.toList());
        return new ErrorResp(BAD_REQUEST_ERROR, errors);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ErrorResp handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        log.error(e.getMessage(), e);
        List<String> errors = e.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> String.format("parameter '%s' %s", fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());
        return new ErrorResp(BAD_REQUEST_ERROR, errors);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public ErrorResp handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e){
        log.error(e.getMessage(), e);
        return new ErrorResp(HTTP_METHOD_NOT_ALLOWED, e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingRequestHeaderException.class)
    @ResponseBody
    public ErrorResp handleMissingRequestHeaderException(MissingRequestHeaderException e){
        return new ErrorResp(BAD_REQUEST_ERROR, e.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResp> handleBusinessException(BusinessException e){
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new ErrorResp(e.getCode(),e.getMessage(),e.getDetails()), HttpStatus.valueOf(e.getHttpStatus()));
    }


}
