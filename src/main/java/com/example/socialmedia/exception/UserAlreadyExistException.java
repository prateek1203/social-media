package com.example.socialmedia.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserAlreadyExistException extends GlobalException{
    private static final long serialVersionUID = 8791634L;
    public UserAlreadyExistException(String message) {
        super(message);
    }
}
