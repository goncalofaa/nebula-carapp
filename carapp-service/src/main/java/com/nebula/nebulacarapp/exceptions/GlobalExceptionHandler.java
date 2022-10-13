package com.nebula.nebulacarapp.exceptions;

import com.mongodb.MongoWriteException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {



    @ExceptionHandler(MongoWriteException.class)
    public ResponseEntity duplicateKeyException(
            MongoWriteException mongoWriteException) {


        Map<String, Object> body = new HashMap<>();
        body.put("description", "Car already exists");

        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity customException(
            CustomException customException) {
        Map<String, Object> body = new HashMap<>();
        if (customException.getMessage() == "Extra parameters are present"|| customException.getMessage() == "Parameters not recognized")  {
            body.put("description", "Incorrect query parameter provided");
        }else if(customException.getMessage() == "Id not matching"){
            body.put("description", "Incorrect id provided");
        }else{
            body.put("description", "General Custom Exception");
        }
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity genericException(ConstraintViolationException constraintViolationException) {
        Set<ConstraintViolation<?>> violations = constraintViolationException.getConstraintViolations();
        Map<String, Object> errorBody = new HashMap<>();

        if (!violations.isEmpty()) {


            violations.forEach(violation -> {
                System.out.println(violation);

                if(violation.getMessage().equals("must not be empty")){

                    errorBody.put("description", "Incorrect car data provided");
                }
            });

        } else {
            errorBody.put("description", "ConstraintViolationException occurred.");
        }
        return new ResponseEntity<>(errorBody, HttpStatus.BAD_REQUEST);
    }
}
