package com.example.socialmedia.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GlobalException extends RuntimeException{
    private static final long serialVersionUID = 7791624L;
    public String message;
}
