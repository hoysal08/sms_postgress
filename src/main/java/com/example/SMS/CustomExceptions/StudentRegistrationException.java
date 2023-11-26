package com.example.SMS.CustomExceptions;



public class StudentRegistrationException extends RuntimeException {

    public StudentRegistrationException() {
        super();
    }

    public StudentRegistrationException(String message) {
        super(message);
    }

    public StudentRegistrationException(String message, Throwable cause) {
        super(message, cause);
    }
}
