package com.example.dogs.service.impl;

import com.example.dogs.entity.DogBreed;
import com.example.dogs.payload.DogBreedDto;
import com.example.dogs.repositories.DogBreedRepository;
import com.example.dogs.service.DogBreedService;
import org.modelmapper.ModelMapper;
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
        DogBreed dogBreed = dogBreedRepository.findById(breedId).orElseThrow(
                () -> new RuntimeException("Breed not found")
        );

        return mapToDto(dogBreed);
    }

    @Override
    public List<DogBreedDto> getAllDogBreeds() {
        List<DogBreed> dogBreeds = dogBreedRepository.findAll();

        return dogBreeds.stream().map(this::mapToDto).toList();
    }

    @Override
    public DogBreedDto updateDogBreed(long breedId, DogBreedDto dogBreedDto) {
        DogBreed dogBreed = dogBreedRepository.findById(breedId).orElseThrow(
                () -> new RuntimeException("Breed not found")
        );


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
        DogBreed dogBreed = dogBreedRepository.findById(breedId).orElseThrow(
                () -> new RuntimeException("Dog breed with id"+breedId+"doesn't already exist"));
        dogBreedRepository.deleteById(breedId);

        return "Dog breed with id"+breedId+"deleted successfully";
    }

    private DogBreed mapToEntity(DogBreedDto dogBreedDto) {
        return mapper.map(dogBreedDto, DogBreed.class);
    }

    private DogBreedDto mapToDto(DogBreed dogBreed) {
        return mapper.map(dogBreed, DogBreedDto.class);
    }

}
