package com.example.manage.core.exception;

import com.example.manage.core.ResultCodes;
import com.example.manage.core.exception.CustomException;
import com.example.manage.core.model.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.AccessDeniedException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponse> exceptionHandler(Exception e) {
        log.error("", e);
        CommonResponse response = new CommonResponse();
        response.setSuccess(false);
        response.setCode(ResultCodes.INTERNAL_SERVER_ERROR.value());
        response.setMessage("요청을 처리하는 과정에서 오류가 발생 하였습니다.<br/>잠시 후 다시 시도 하시기 바랍니다.");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CommonResponse> biztalkExceptionHandler(CustomException e) {
        log.error("", e);
        CommonResponse response = new CommonResponse();
        response.setSuccess(false);
        response.setCode(e.getErrorCode());
        response.setMessage(e.getLocalizedMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<CommonResponse> accessDeniedExceptionHandler(AccessDeniedException e) {
        CommonResponse response = new CommonResponse();
        response.setSuccess(false);
        response.setCode(ResultCodes.FORBIDDEN.value());
        response.setMessage(ResultCodes.FORBIDDEN.getReasonPhrase());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    public ResponseEntity<CommonResponse> authenticationCredentialsNotFoundExceptionHandler(AuthenticationCredentialsNotFoundException e) {
        CommonResponse response = new CommonResponse();
        response.setSuccess(false);
        response.setCode(ResultCodes.UNAUTHORIZED.value());
        response.setMessage(ResultCodes.UNAUTHORIZED.getReasonPhrase());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<CommonResponse> httpRequestMethodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException e) {
        CommonResponse response = new CommonResponse();
        response.setSuccess(false);
        response.setCode(HttpStatus.METHOD_NOT_ALLOWED.value());
        response.setMessage(e.getLocalizedMessage());

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(response);
    }

    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class, MissingServletRequestParameterException.class})
    public ResponseEntity<CommonResponse> badRequestExceptionHandler(Exception e, HttpServletRequest request) {
        CommonResponse response = new CommonResponse();
        response.setSuccess(false);
        response.setCode(HttpStatus.BAD_REQUEST.value());
        response.setMessage(e.getLocalizedMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
    public ResponseEntity<CommonResponse> httpMediaTypeNotSupportedExceptionHandler(HttpMediaTypeNotSupportedException e, HttpServletRequest request) {
        CommonResponse response = new CommonResponse();
        response.setSuccess(false);
        response.setCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
        response.setMessage(e.getLocalizedMessage());

        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(response);
    }
}
