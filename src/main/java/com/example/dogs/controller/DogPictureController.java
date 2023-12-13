package com.example.dogs.controller;

import com.example.dogs.service.DogPictureService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

@RestController
@RequestMapping("/api/v1/dogs/{breedId}/pictures")
public class DogPictureController {
    public DogPictureService dogPictureService;

    public DogPictureController(DogPictureService dogPictureService) {
        this.dogPictureService = dogPictureService;
    }

    @PostMapping("/single")
    ResponseEntity<String> addPicture(@RequestParam("image") MultipartFile image,
                                      @PathVariable long breedId) throws IOException {
        return ResponseEntity.ok(dogPictureService.addDogPicture(image, breedId));
    }

    @PostMapping
    ResponseEntity<String> addMultiplePictures(@RequestParam("images") MultipartFile[] image,
                                      @PathVariable long breedId) throws IOException {
        return ResponseEntity.ok(dogPictureService.addMultipleDogPictures(image, breedId));
    }

    @GetMapping("/{pictureId}")
    ResponseEntity<?> getPictureById(@PathVariable long breedId,
                                     @PathVariable long pictureId ) {

        byte[] data = dogPictureService.getDogPicture(breedId, pictureId);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf(IMAGE_PNG_VALUE))
                .body(data);
    }

    @GetMapping
    ResponseEntity<List<byte[]>> getAllPictures(@PathVariable long breedId){

        return ResponseEntity.ok(dogPictureService.getBreedPictures(breedId));
    }

    // THIS ONE DOESN'T WORK
//    @GetMapping
//    public ResponseEntity<List<?>> getAllPictures(@PathVariable long breedId){
//
//        var images = dogPictureService.getBreedPictures(breedId);
//
//        return ResponseEntity.status(HttpStatus.OK)
//                .contentType(MediaType.IMAGE_PNG)
//                .body(images);
//    }


}
