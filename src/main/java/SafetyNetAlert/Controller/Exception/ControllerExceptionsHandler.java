package SafetyNetAlert.Controller.Exception;

import Config.Generated;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.naming.NameNotFoundException;
import java.util.Date;

@ControllerAdvice
@Generated
@Slf4j
public class ControllerExceptionsHandler {

    @ExceptionHandler(value = {NameNotFoundException.class})
    public ResponseEntity<ErrorMessage> UserDontExistException(NameNotFoundException e){
       // return "The credentials used dont match any user"+e.getMessage();
        ErrorMessage message = new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                e.getMessage());
        log.error(e.getMessage());

        return new ResponseEntity<ErrorMessage>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {PersonsNotFoundException.class})
    public ResponseEntity<ErrorMessage> PersonDontExistException(PersonsNotFoundException e){
        ErrorMessage message = new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                e.getMessage());
        log.error(e.getMessage());

        return new ResponseEntity<ErrorMessage>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {MedicalRecordsNotFoundException.class})
    public ResponseEntity<ErrorMessage> MedicalRecordsDontExistException(MedicalRecordsNotFoundException e){
        ErrorMessage message = new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                e.getMessage());
        log.error(e.getMessage());

        return new ResponseEntity<ErrorMessage>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {FirestationStationNotFoundException.class})
    public ResponseEntity<ErrorMessage> FirestationStationNotFoundException(FirestationStationNotFoundException e){
        ErrorMessage message = new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                e.getMessage());
        log.error(e.getMessage());

        return new ResponseEntity<ErrorMessage>(message, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(value = {PersonInParameterIsNullException.class})
    public ResponseEntity<ErrorMessage> PersonInParameterIsNullException(PersonInParameterIsNullException e){
        ErrorMessage message = new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                e.getMessage());
        log.error(e.getMessage());

        return new ResponseEntity<ErrorMessage>(message, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(value = {FirestationNotFoundException.class})
    public ResponseEntity<ErrorMessage> FirestationNotFoundException(FirestationNotFoundException e){
        ErrorMessage message = new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                e.getMessage());
        log.error(e.getMessage());

        return new ResponseEntity<ErrorMessage>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {ListIsNullException.class})
    public ResponseEntity<ErrorMessage> ListIsNullException(ListIsNullException e){
        ErrorMessage message = new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                e.getMessage());
        log.error(e.getMessage());

        return new ResponseEntity<ErrorMessage>(message, HttpStatus.NOT_FOUND);
    }
}
