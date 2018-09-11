package com.backbase.kalaha.kalahaGame.exception;

/**
 * @author  anumfatima
 * @desc throws when business rules of the game are violated.
 */
public class KalahBusinessException extends KalahException {

    public KalahBusinessException(String message) {
        super(message);
    }

    public int getHttpErrorCode() {
        return 400;
    }
}