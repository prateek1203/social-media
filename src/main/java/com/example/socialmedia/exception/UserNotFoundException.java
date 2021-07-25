package com.example.socialmedia.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserNotFoundException extends GlobalException {
    private static final long serialVersionUID = 8791624L;

    public UserNotFoundException(String message) {
        super(message);
    }
}
