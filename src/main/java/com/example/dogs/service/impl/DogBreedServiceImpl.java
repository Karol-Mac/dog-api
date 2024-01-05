package com.example.dogs.service.impl;

import com.example.dogs.entity.DogBreed;
import com.example.dogs.exception.DogApiBadRequestException;
import com.example.dogs.exception.DogApiNotFoundException;
import com.example.dogs.payload.DogBreedDto;
import com.example.dogs.repositories.DogBreedRepository;
import com.example.dogs.service.DogBreedService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DogBreedServiceImpl implements DogBreedService {

    private final DogBreedRepository dogBreedRepository;
    private final ModelMapper mapper;

    public DogBreedServiceImpl(DogBreedRepository dogBreedRepository, ModelMapper mapper) {
        this.dogBreedRepository = dogBreedRepository;
        this.mapper = mapper;
    }

    @Override
    public DogBreedDto createDogBreed(DogBreedDto dogBreedDto) {
        DogBreed dogBreed = mapToEntity(dogBreedDto);

        dogBreed = dogBreedRepository.save(dogBreed);
        return mapToDto(dogBreed);
    }

    @Override
    public DogBreedDto getDogBreed(long breedId) {
        DogBreed dogBreed = getDogBreedFromDB(breedId);

        return mapToDto(dogBreed);
    }

    @Override
    public List<DogBreedDto> getAllDogBreeds() {
        List<DogBreed> dogBreeds = dogBreedRepository.findAll();

        return dogBreeds.stream().map(this::mapToDto).toList();
    }

    @Override
    public DogBreedDto updateDogBreed(long breedId, DogBreedDto dogBreedDto) {
        DogBreed dogBreed = getDogBreedFromDB(breedId);

        dogBreed.setId(breedId);
        dogBreed.setName(dogBreedDto.getName());
        dogBreed.setDescription(dogBreedDto.getDescription());

        dogBreed.setLifespanMin(dogBreedDto.getLifespanMin());
        dogBreed.setLifespanMax(dogBreedDto.getLifespanMax());

        dogBreed.setDogSize(dogBreedDto.getDogSize());
        dogBreed.setFurType(dogBreedDto.getFurType());
        dogBreed.setBehaviourType(dogBreedDto.getBehaviourType());
        dogBreed.setBarkingFrequency(dogBreedDto.getBarkingFrequency());

        dogBreed = dogBreedRepository.save(dogBreed);

        return mapToDto(dogBreed);
    }

    @Override
    public String deleteDogBreed(long breedId) {
        DogBreed dogBreed = getDogBreedFromDB(breedId);
        dogBreedRepository.deleteById(breedId);

        return "Dog breed with id "+breedId+" deleted successfully";
    }

    @Override
    public List<DogBreedDto> filterDogBreeds(List<String> sizes,
                                          List<String> barkingFrequencies, List<String> furTypes,
                                          List<String> behaviourTypes) {

        List<DogBreed> dogBreeds = dogBreedRepository.findAll();

        return dogBreeds.stream()
                .filter(dogBreed -> filterBySize(dogBreed, sizes))
                .filter(dogBreed -> filterByBarkingFrequency(dogBreed, barkingFrequencies))
                .filter(dogBreed -> filterByFurType(dogBreed, furTypes))
                .filter(dogBreed -> filterByBehaviourType(dogBreed, behaviourTypes))
                .map(this::mapToDto)
                .toList();
    }

    private boolean filterBySize(DogBreed dogBreed, List<String> sizes) {
        return sizes == null || sizes.isEmpty() || sizes.contains(dogBreed.getDogSize().getSize());
    }

    private boolean filterByBarkingFrequency(DogBreed dogBreed, List<String> barkingFrequencies) {
        return barkingFrequencies == null || barkingFrequencies.isEmpty() || barkingFrequencies.contains(dogBreed.getBarkingFrequency().getFrequency());
    }

    private boolean filterByFurType(DogBreed dogBreed, List<String> furTypes) {
        return furTypes == null || furTypes.isEmpty() || furTypes.contains(dogBreed.getFurType().getFur());
    }

    private boolean filterByBehaviourType(DogBreed dogBreed, List<String> behaviourTypes) {
        return behaviourTypes == null || behaviourTypes.isEmpty() || behaviourTypes.contains(dogBreed.getBehaviourType().getBehavior());
    }

    private DogBreed getDogBreedFromDB(long breedId) {
        return dogBreedRepository.findById(breedId).orElseThrow(
                () -> new DogApiBadRequestException(HttpStatus.BAD_REQUEST,"Breed with id = " + breedId + " not found")
        );
    }


    private DogBreed mapToEntity(DogBreedDto dogBreedDto) {
        return mapper.map(dogBreedDto, DogBreed.class);
    }

    private DogBreedDto mapToDto(DogBreed dogBreed) {
        return mapper.map(dogBreed, DogBreedDto.class);
    }

}
