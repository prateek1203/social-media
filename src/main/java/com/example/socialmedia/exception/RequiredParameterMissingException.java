package com.example.socialmedia.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class RequiredParameterMissingException extends GlobalException {
    public RequiredParameterMissingException(String message) {
        super(message);
    }
}
