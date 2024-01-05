package com.example.dogs.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class DogApiMultipartException extends RuntimeException{
    private HttpStatus status = null; //jak jest finale to nie mozna zmienic jej wartosci
    private String message = null;

    public DogApiMultipartException(HttpStatus status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus(){
        return this.status;
    }

    @Override
    public String getMessage(){
        return this.message;
    }

    @Override
    public synchronized Throwable fillInStackTrace(){
        return null;
    }

}
