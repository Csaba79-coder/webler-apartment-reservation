package hu.webler.weblerapartmentreservation.address.controller.exception;

import hu.webler.weblerapartmentreservation.address.value.AddressErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;
import java.util.NoSuchElementException;

import static hu.webler.weblerapartmentreservation.address.value.AddressErrorCode.ERROR_CODE;

@ControllerAdvice
public class AddressControllerExceptionHandler {

    @ExceptionHandler(value = {NoSuchElementException.class})
    public ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException ex) {
        return new ResponseEntity<>(responseBodyWithMessage(ERROR_CODE, ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    private String responseBodyWithMessage(AddressErrorCode code, String message) {return Map.of(code, message).toString();}
}
