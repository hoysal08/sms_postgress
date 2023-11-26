package com.example.SMS.CustomExceptions;



public class StudentWithdrawalException extends RuntimeException {

    public StudentWithdrawalException() {
        super();
    }

    public StudentWithdrawalException(String message) {
        super(message);
    }

    public StudentWithdrawalException(String message, Throwable cause) {
        super(message, cause);
    }
}
