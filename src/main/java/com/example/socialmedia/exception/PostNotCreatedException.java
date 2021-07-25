package com.example.socialmedia.exception;


import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PostNotCreatedException extends GlobalException{
    public PostNotCreatedException(String message) {
        super(message);
    }
}
