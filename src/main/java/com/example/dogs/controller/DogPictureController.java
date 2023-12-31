package com.example.dogs.controller;

import com.example.dogs.exception.DogApiBadRequestException;
import com.example.dogs.exception.DogApiNotFoundException;
import com.example.dogs.exception.DogBreedPictureException;
import com.example.dogs.exception.DogApiMultipartException;
import com.example.dogs.payload.DogPictureDto;
import com.example.dogs.service.DogPictureService;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


import com.example.dogs.exception.DogApiInternalServerErrorException;

import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

@RestController
@RequestMapping("/api/v1/dogs/{breedId}/pictures")
public class DogPictureController {
    public DogPictureService dogPictureService;

    public DogPictureController(DogPictureService dogPictureService) {
        this.dogPictureService = dogPictureService;
    }

    @PostMapping("/single")
    ResponseEntity<Long> addPicture(@RequestParam("image") MultipartFile image,
                                      @PathVariable long breedId) {
        try{
            return ResponseEntity.ok(dogPictureService.addDogPicture(image, breedId));
        }catch (MultipartException e) {
            throw new DogApiInternalServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Picture 1 addtion failure");
        }catch (IOException e){
            throw new DogApiInternalServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Picture 2 addtion failure");
        }catch (RuntimeException e){
            throw new DogApiInternalServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Picture 2 addtion failure");
        }
    }



    @PostMapping
    ResponseEntity<List<Long>> addMultiplePictures(@RequestParam("images") MultipartFile[] image,
                                      @PathVariable long breedId) {
        try{
            return ResponseEntity.ok(dogPictureService.addMultipleDogPictures(image, breedId));
        }catch(IOException e){
            throw new DogApiMultipartException(HttpStatus.INTERNAL_SERVER_ERROR, "Failure while trying to access all breed pictures");
        }
    }

    @GetMapping("/{pictureId}/image")
    ResponseEntity<?> getPictureById(@PathVariable long breedId,
                                     @PathVariable long pictureId ) {
        try {
            byte[] data = dogPictureService.getDogPicture(breedId, pictureId);

            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.valueOf(IMAGE_PNG_VALUE))
                    .body(data);
        }catch(RuntimeException e){
            throw new DogApiNotFoundException(HttpStatus.NOT_FOUND , "Failure get : pictureId ", "/dogs/"+breedId+"/pictures/"+pictureId);
        }
    }

    @GetMapping("/{pictureId}")
    ResponseEntity<DogPictureDto> getPictureObject(@PathVariable long breedId,
                                                   @PathVariable long pictureId ) {
        try {
            return ResponseEntity.ok(dogPictureService.retrieveDogPicture(breedId, pictureId));
        }catch(RuntimeException e){
            throw new DogApiNotFoundException(HttpStatus.NOT_FOUND , "Failure get : picture ", "/dogs/"+breedId+"/pictures/"+pictureId+"/object");
        }
    }

    @GetMapping
    ResponseEntity<List<DogPictureDto>> getAllPictures(@PathVariable long breedId){
        try {
        return ResponseEntity.ok(dogPictureService.getBreedPictures(breedId));
        }catch(RuntimeException e){
            throw new DogApiBadRequestException(HttpStatus.BAD_REQUEST, "Failure while trying to access all breed pictures");
        }
    }

    // THIS ONE DOESN'T WORK
//    @GetMapping
//    public ResponseEntity<?> getAllPictures(@PathVariable long breedId){
//
//        var images = dogPictureService.getBreedPictures(breedId);
//
//        return ResponseEntity.status(HttpStatus.OK)
//                .contentType(MediaType.IMAGE_PNG)
//                .body(images);
//    }

    public ResponseEntity<?> updateDogPicture(@PathVariable long breedId,
                                       @PathVariable long pictureId,
                                       @RequestParam MultipartFile image){
        try{
            byte[] data = dogPictureService.updateDogPicture(pictureId, image);

            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.valueOf(IMAGE_PNG_VALUE))
                    .body(data);
        }catch(IOException e){
            throw new DogApiBadRequestException(HttpStatus.BAD_REQUEST, "Failure while trying to access all breed pictures");
        }
    }

    @DeleteMapping("/{pictureId}")
    public ResponseEntity<String> deleteDogPicture(@PathVariable long breedId,
                                                   @PathVariable long pictureId){
        try{
            return ResponseEntity.ok(dogPictureService.deleteDogPicture(pictureId));
        }catch(RuntimeException e){
            throw new DogApiBadRequestException(HttpStatus.BAD_REQUEST, "Failure to delete picture /dogs/"+breedId+"/pictures"+pictureId);
        }
    }
  
}
