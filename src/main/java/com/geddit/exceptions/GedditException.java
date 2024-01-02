package com.geddit.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class GedditException extends RuntimeException {
    private HttpStatus httpStatus;
    private String message;
    private String devErrorMessage;

    public GedditException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
        this.message = message;
    }
    public GedditException(String message, HttpStatus httpStatus, String devErrorMessage) {
        super(message);
        this.httpStatus = httpStatus;
        this.message = message;
        this.devErrorMessage = devErrorMessage;
    }
    public GedditException(String message, String devErrorMessage) {
        super(message);
        this.httpStatus = HttpStatus.BAD_REQUEST;
        this.message = message;
        this.devErrorMessage = devErrorMessage;
    }
    public GedditException(String message) {
        super(message);
        this.httpStatus = HttpStatus.BAD_REQUEST;
        this.message = message;
    }
}
