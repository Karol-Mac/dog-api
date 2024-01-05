package com.example.dogs.service;

import com.example.dogs.payload.DogBreedDto;

import java.util.List;

public interface DogBreedService {
    DogBreedDto createDogBreed(DogBreedDto dogBreedDto);

    DogBreedDto getDogBreed(long breedId);

    List<DogBreedDto> getAllDogBreeds();

    DogBreedDto updateDogBreed(long breedId, DogBreedDto dogBreedDto);

    String deleteDogBreed(long breedId);

    List<DogBreedDto> filterDogBreeds(List<String> sizes,
                                             List<String> barkingFrequencies, List<String> furTypes,
                                             List<String> behaviourTypes);

}
