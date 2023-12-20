package com.example.dogs.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class DogApiBadRequestException extends RuntimeException {
    private HttpStatus status = null; //jak jest finale to nie mozna zmienic jej wartosci
    private String message = null;

    public DogApiBadRequestException(HttpStatus status, String message) {
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

}
