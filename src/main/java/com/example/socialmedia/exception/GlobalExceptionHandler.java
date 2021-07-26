package com.example.socialmedia.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleException(UserNotFoundException exception) {
        Map<String, String> details = new HashMap<>();
        details.put("error",exception.getMessage());
        ErrorResponse error = new ErrorResponse("SERVER ERROR", details);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({GlobalException.class})
    public ResponseEntity<ErrorResponse> handleException(GlobalException exception) {
        Map<String, String> details = new HashMap<>();
        details.put("error",exception.getMessage());
        ErrorResponse error = new ErrorResponse("SERVER ERROR", details);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NoNewsFeedException.class})
    public ResponseEntity<ErrorResponse> handleException(NoNewsFeedException exception) {
        Map<String, String> details = new HashMap<>();
        details.put("error",exception.getMessage());
        ErrorResponse error = new ErrorResponse("SERVER ERROR", details);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({PostNotCreatedException.class})
    public ResponseEntity<ErrorResponse> handleException(PostNotCreatedException exception) {
        Map<String, String> details = new HashMap<>();
        details.put("error",exception.getMessage());
        ErrorResponse error = new ErrorResponse("SERVER ERROR", details);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({RequiredParameterMissingException.class})
    public ResponseEntity<ErrorResponse> handleException(RequiredParameterMissingException exception) {
        Map<String, String> details = new HashMap<>();
        details.put("error",exception.getMessage());
        ErrorResponse error = new ErrorResponse("SERVER ERROR", details);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}

