package com.example.dogs.controller;


import com.example.dogs.payload.DogBreedDto;
import com.example.dogs.service.DogBreedService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class DogBreedController {

    private final DogBreedService dogBreedService;

    public DogBreedController(DogBreedService dogBreedService) {
        this.dogBreedService = dogBreedService;
    }

    @GetMapping("/dogs")
    public ResponseEntity<List<DogBreedDto>> getDogs(){
        return ResponseEntity.ok(dogBreedService.getAllDogBreeds());
    }

    @GetMapping("/dogs/{breedId}")
    public ResponseEntity<DogBreedDto> getDogById(@PathVariable Long breedId){
        return ResponseEntity.ok(dogBreedService.getDogBreed(breedId));
    }

    @PostMapping("/dogs")
    public ResponseEntity<DogBreedDto> createDog(@RequestBody DogBreedDto dogBreedDto){
        return new ResponseEntity<>(
                dogBreedService.createDogBreed(dogBreedDto), HttpStatus.CREATED);
    }

    @PutMapping("/dogs/{breedId}")
    public ResponseEntity<DogBreedDto> updateDog(@PathVariable Long breedId, @RequestBody DogBreedDto dogBreedDto){
        return ResponseEntity.ok(
                dogBreedService.updateDogBreed(breedId, dogBreedDto));
    }

    @DeleteMapping("/dogs/{breedId}")
    public ResponseEntity<String> deleteDog(@PathVariable Long breedId){
        return ResponseEntity.ok(
                dogBreedService.deleteDogBreed(breedId));
    }

}
