package com.mpautasso.sistemavtv.exceptions;


import com.mpautasso.sistemavtv.exceptions.custom.EntityAlreadyExistsException;
import com.mpautasso.sistemavtv.exceptions.custom.EntityNotFoundException;
import com.mpautasso.sistemavtv.exceptions.custom.InvalidArgumentException;
import com.mpautasso.sistemavtv.exceptions.custom.NoSuchEntityExistsException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ApiExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            EntityNotFoundException.class,
            EntityAlreadyExistsException.class,
            NoSuchEntityExistsException.class,
            InvalidArgumentException.class
    })
    @ResponseBody
    public ErrorMessage badRequest(HttpServletRequest request, Exception exception){
        return new ErrorMessage(exception, request.getRequestURI());
    }
}
