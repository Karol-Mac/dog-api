package com.example.dogs.controller;


import com.example.dogs.payload.DogBreedDto;
import com.example.dogs.service.DogBreedService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


import com.example.dogs.exception.DogApiBadRequestException;
import com.example.dogs.exception.DogApiNotFoundException;

@RestController
@RequestMapping("/api/v1")
public class DogBreedController {

    private final DogBreedService dogBreedService;

    public DogBreedController(DogBreedService dogBreedService) {
        this.dogBreedService = dogBreedService;
    }

    @GetMapping("/dogs")
    public ResponseEntity<List<DogBreedDto>> getDogs(){
        try {
            return ResponseEntity.ok(dogBreedService.getAllDogBreeds());
        }catch(RuntimeException e){
            throw new DogApiBadRequestException(HttpStatus.BAD_REQUEST, "Failure to get any DogBreed");
        }
    }

    @GetMapping("/dogs/{breedId}")
    public ResponseEntity<DogBreedDto> getDogById(@PathVariable Long breedId){
        try {
            return ResponseEntity.ok(dogBreedService.getDogBreed(breedId));
        }catch(RuntimeException e){
            throw new DogApiNotFoundException(HttpStatus.NOT_FOUND , "Failure get : BreedId == ", "/dogs/"+breedId);
        }
    }

    @PostMapping("/dogs")
    public ResponseEntity<DogBreedDto> createDog(@RequestBody DogBreedDto dogBreedDto){
        try{
            return new ResponseEntity<>(dogBreedService.createDogBreed(dogBreedDto), HttpStatus.CREATED);
        }catch(RuntimeException e){
            throw new DogApiBadRequestException(HttpStatus.BAD_REQUEST, "Failure to create dog breed");
        }
    }

    @PutMapping("/dogs/{breedId}")
    public ResponseEntity<DogBreedDto> updateDog(@PathVariable Long breedId, @RequestBody DogBreedDto dogBreedDto){
        try{
            return ResponseEntity.ok(dogBreedService.updateDogBreed(breedId, dogBreedDto));
        }catch(RuntimeException e){
            throw new DogApiBadRequestException(HttpStatus.BAD_REQUEST, "Failure to update dog breed with id == "+breedId);
        }
    }

    @DeleteMapping("/dogs/{breedId}")
    public ResponseEntity<String> deleteDog(@PathVariable Long breedId){
        try{
            return ResponseEntity.ok(dogBreedService.deleteDogBreed(breedId));
        }catch(RuntimeException e){
            throw new DogApiNotFoundException(HttpStatus.NOT_FOUND , "Failure delete : BreedId == ", "/dogs/"+breedId);
        }
    }

}
