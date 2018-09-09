package com.backbase.kalaha.kalahaGame.exception;

public class BusinessException extends KalahException {

    public BusinessException(String message) {
        super(message);
    }

    public int getHttpErrorCode() {
        return 400;
    }
}