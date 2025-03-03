package com.example.winterdeom.domain.common.exception.controller;

import com.example.winterdeom.domain.common.error.ErrorCode;
import com.example.winterdeom.domain.common.exception.CustomException;
import com.example.winterdeom.domain.common.exception.DtoValidationException;
import com.example.winterdeom.domain.common.exception.dto.ErrorResponseDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponseDto> handleCustomException(CustomException customException){
        writeLog(customException);
        HttpStatus httpStatus = this.resolveHttpStatus(customException);
        return new ResponseEntity<>(ErrorResponseDto.res(customException), httpStatus);
    }

    @ExceptionHandler({ValidationException.class, MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponseDto> handleCustomException(MethodArgumentNotValidException methodArgumentNotValidException){
        FieldError fieldError = methodArgumentNotValidException.getBindingResult().getFieldError();
        if(fieldError == null){
            return new ResponseEntity<>(ErrorResponseDto.res(String.valueOf(HttpStatus.BAD_REQUEST.value()),
                    methodArgumentNotValidException), HttpStatus.BAD_REQUEST);
        }
        ErrorCode validationErrorCode = ErrorCode.resolveValidationErrorCode(fieldError.getCode());
        String detail = fieldError.getDefaultMessage();
        DtoValidationException dtoValidationException = new DtoValidationException(validationErrorCode, detail);
        this.writeLog(dtoValidationException);
        return new ResponseEntity<>(ErrorResponseDto.res(dtoValidationException),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleEntityNotFoundException(EntityNotFoundException entityNotFoundException){
        writeLog(entityNotFoundException);
        return new ResponseEntity<>(ErrorResponseDto.res(String.valueOf(HttpStatus.NOT_FOUND.value()),entityNotFoundException), HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleExceptioon(Exception exception){
        this.writeLog(exception);
        return new ResponseEntity<>(
                ErrorResponseDto.res(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), exception),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    private void writeLog(CustomException customException){
        String exceptionName = customException.getClass().getSimpleName();
        ErrorCode errorCode = customException.getErrorCode();
        String detail = customException.getDetail();
        log.error("[{}]{}:{}", exceptionName,errorCode.getMessage(), detail);
    }

    private void writeLog(Exception exception){
        String exceptionName = exception.getClass().getSimpleName();
        String message = exception.getMessage();
        log.error("[{}]:{}", exceptionName, message);
    }

    private HttpStatus resolveHttpStatus(CustomException customException){
        return HttpStatus.resolve(Integer.parseInt(customException.getErrorCode().getCode().substring(0,3)));
    }
}
