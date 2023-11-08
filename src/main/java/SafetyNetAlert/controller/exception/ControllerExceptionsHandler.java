package SafetyNetAlert.controller.exception;


import SafetyNetAlert.config.Generated;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
@Generated
@Slf4j
public class ControllerExceptionsHandler {

    @ExceptionHandler(value = {ParameterIsNullException.class})
    public ResponseEntity<ErrorMessage> ParameterIsNullException(ParameterIsNullException e){
       // return "The credentials used dont match any user"+e.getMessage();
        ErrorMessage message = new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                e.getMessage());
        log.error(e.getMessage());

        return new ResponseEntity<ErrorMessage>(message, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<ErrorMessage> NotFoundException(NotFoundException e){
        ErrorMessage message = new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                e.getMessage());
        log.error(e.getMessage());

        return new ResponseEntity<ErrorMessage>(message, HttpStatus.NOT_FOUND);
    }

}
