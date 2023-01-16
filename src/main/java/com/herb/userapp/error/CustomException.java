package com.herb.userapp.error;

public class CustomException extends RuntimeException {
    public CustomException(String message) {
        super(message);
    }

    public CustomException() {
        super();
    }
}
