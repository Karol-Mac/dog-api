package com.example.dogs.controller;


import com.example.dogs.filters.DogBreedFilter;
import com.example.dogs.filters.FilterPreference;
import com.example.dogs.payload.DogBreedDto;
import com.example.dogs.service.DogBreedService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/dogs")
public class DogBreedController {

    private final DogBreedService dogBreedService;

    private final DogBreedFilter dogBreedFilter;

    public DogBreedController(DogBreedService dogBreedService, DogBreedFilter dogBreedFilter) {
        this.dogBreedService = dogBreedService;
        this.dogBreedFilter = dogBreedFilter;
    }

    @GetMapping
    public ResponseEntity<List<DogBreedDto>> getDogs(){
        return ResponseEntity.ok(dogBreedService.getAllDogBreeds());
    }

    @GetMapping("/{breedId}")
    public ResponseEntity<DogBreedDto> getDogById(@PathVariable Long breedId){
        return ResponseEntity.ok(dogBreedService.getDogBreed(breedId));
    }


    @GetMapping("/filter")
    public ResponseEntity<List<DogBreedDto>> filterDogs(
                        @RequestParam(required = false, name = "sizes") List<String> sizes,
                        @RequestParam(required = false, name = "barkingFrequencies") List<String> barkingFrequencies,
                        @RequestParam(required = false, name = "furTypes") List<String> furTypes,
                        @RequestParam(required = false, name = "behaviourTypes") List<String> behaviourTypes){

        return ResponseEntity.ok(dogBreedService.filterDogBreeds(sizes,
                                    barkingFrequencies, furTypes, behaviourTypes));
    }

    @GetMapping("/filterPreference")
    public List<DogBreedDto> filterDogs(@RequestBody List<FilterPreference> filterPreferenceList) {
        return dogBreedFilter.filterDogBreeds(filterPreferenceList);
    }



    @PostMapping
    public ResponseEntity<DogBreedDto> createDog(@RequestBody DogBreedDto dogBreedDto){
        return new ResponseEntity<>(
                dogBreedService.createDogBreed(dogBreedDto), HttpStatus.CREATED);
    }

    @PutMapping("/{breedId}")
    public ResponseEntity<DogBreedDto> updateDog(@PathVariable Long breedId, @RequestBody DogBreedDto dogBreedDto){
        return ResponseEntity.ok(
                dogBreedService.updateDogBreed(breedId, dogBreedDto));
    }

    @DeleteMapping("/{breedId}")
    public ResponseEntity<String> deleteDog(@PathVariable Long breedId){
        return ResponseEntity.ok(
                dogBreedService.deleteDogBreed(breedId));
    }

}
