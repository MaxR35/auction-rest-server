package com.rougeux.projet.auction.exception;

import com.rougeux.projet.auction.api.ApiResponse;
import com.rougeux.projet.auction.api.ApiResponseFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<String>> handleAccessDeniedException() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                ApiResponseFactory.error("403", "request.forbidden")
        );
    }

    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleAuthenticationException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                ApiResponseFactory.error("401", "request.unauthorized")
        );
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleNoHandlerFoundException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ApiResponseFactory.error("404", "request.notFound")
        );
    }

    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            MethodArgumentTypeMismatchException.class,
            HttpMessageNotReadableException.class
    })
    public ResponseEntity<ApiResponse<String>> handleBadRequestException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApiResponseFactory.error("400", "request.badRequest")
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleInternalServerErrorException() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                ApiResponseFactory.error("500", "request.internal.error")
        );
    }
}
