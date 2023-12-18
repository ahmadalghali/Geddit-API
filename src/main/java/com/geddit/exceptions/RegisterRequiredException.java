package com.geddit.exceptions;

public class RegisterRequiredException extends RuntimeException{
    public RegisterRequiredException() {
        super("Please register");
    }
}
