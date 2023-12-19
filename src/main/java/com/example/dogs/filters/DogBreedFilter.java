package com.example.dogs.filters;

import com.example.dogs.entity.DogBreed;
import com.example.dogs.payload.DogBreedDto;
import com.example.dogs.repositories.DogBreedRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DogBreedFilter {

    private static final double MINIMUM_SIMILARITY_THRESHOLD = 70;
    private final DogBreedRepository dogBreedRepository;
    private final ModelMapper mapper;

    public DogBreedFilter(DogBreedRepository dogBreedRepository, ModelMapper mapper) {
        this.dogBreedRepository = dogBreedRepository;
        this.mapper = mapper;
    }

    public List<DogBreedDto> filterDogBreeds(List<FilterPreference> filterPreferenceList) {
        List<DogBreed> dogBreeds = dogBreedRepository.findAll();

        if (filterPreferenceList.isEmpty()) {
            return dogBreeds.stream().map(this::mapToDto).toList();
        }

        return dogBreeds.stream()
                .filter(dogBreed -> isDogBreedAccepted(dogBreed, filterPreferenceList))
                .map(this::mapToDto)
                .toList();
    }

    private boolean isDogBreedAccepted(DogBreed dogBreed, List<FilterPreference> filterPreferenceList) {
        int totalScore = 0;
        int maxPossibleScore = filterPreferenceList.size() * 100;

        totalScore += filterPreferenceList.stream()
                .mapToInt(preference -> calculateScoreForCriteria(dogBreed, preference)).sum();



        return ((double) totalScore / maxPossibleScore) * 100 >= MINIMUM_SIMILARITY_THRESHOLD;
    }

    private int calculateScoreForCriteria(DogBreed dogBreed, FilterPreference preference) {
        return switch (preference.getPreferencesType()) {
            case SIZE ->
                    dogBreed.getDogSize().getSize().equals(preference.getValue()) ? 100 : 0;
            case BARKING_FREQUENCY ->
                    dogBreed.getBarkingFrequency().getFrequency().equals(preference.getValue()) ? 100 : 0;
            case BEHAVIOUR_TYPE ->
                    dogBreed.getBehaviourType().getBehavior().equals(preference.getValue()) ? 100 : 0;
            case FUR_TYPE ->
                    dogBreed.getFurType().getFur().equals(preference.getValue()) ? 100 : 0;
        };
    }
    private DogBreedDto mapToDto(DogBreed dogBreed) {
        return mapper.map(dogBreed, DogBreedDto.class);
    }
}

