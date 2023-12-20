package com.example.dogs.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class DogBreedPictureException extends RuntimeException {

    private long breedIdValue;
    private long pictureIdValue;

    public DogBreedPictureException(long breedIdValue, long pictureIdValue) {
        super("Failure to find picture on : /dogs/"+breedIdValue+"/pictures/"+pictureIdValue);
        this.breedIdValue = breedIdValue;
        this.pictureIdValue = pictureIdValue;
    }
}
