package com.example.socialmedia.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NoNewsFeedException extends GlobalException {
    private static final long serialVersionUID = 8891624L;

    public NoNewsFeedException(String message) {
        super(message);
    }
}
