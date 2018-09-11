package com.backbase.kalaha.kalahaGame.exception.handler;


import com.backbase.kalaha.kalahaGame.exception.KalahBusinessException;
import com.backbase.kalaha.kalahaGame.exception.KalahException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author afatima
 * Kalah Exception Handler
 *
 */
@Slf4j
@ControllerAdvice
public class KalahExceptionHandler {

    private static final String CONTENT_TYPE = "Content-Type";
    private static final String APPLICATION_JSON_CHARSET_UTF_8 = "application/json; charset=utf-8";

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(KalahBusinessException.class)
    @ResponseBody
    public MessageError handleBusinessException(KalahBusinessException ex) {
        log.error("Business error: {}", ExceptionUtils.getMessage(ex));
        return new MessageError(ex.getLocalizedMessage());
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public MessageError handleIllegalArgumentException(IllegalArgumentException ex) {
        log.error("Validation error: {}", ExceptionUtils.getMessage(ex));
        return new MessageError(ex.getLocalizedMessage());
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(KalahException.class)
    @ResponseBody
    public MessageError handleException(KalahException ex) {
        log.error("Exception not expected: {}", ExceptionUtils.getMessage(ex), ex);
        return new MessageError(ex.getLocalizedMessage());
    }

    @ResponseBody
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public MessageError handleExceptionInternal(Exception ex) {
        log.error("Exception not expected: {}", ExceptionUtils.getMessage(ex), ex);
        return new MessageError(ex.getLocalizedMessage());
    }

    private HttpEntity<MessageError> getMessageErrorHttpEntity(KalahException ex, HttpStatus httpStatus) {
        MessageError messageError = new MessageError(ExceptionUtils.getMessage(ex));
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(CONTENT_TYPE, APPLICATION_JSON_CHARSET_UTF_8);
        return new ResponseEntity<>(messageError, responseHeaders, httpStatus);
    }
}