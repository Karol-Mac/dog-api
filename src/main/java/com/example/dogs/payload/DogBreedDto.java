package com.example.dogs.payload;

import com.example.dogs.enums.BarkingFrequency;
import com.example.dogs.enums.BehaviourType;
import com.example.dogs.enums.DogSize;
import com.example.dogs.enums.FurType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DogBreedDto {
    private long id;
    private String name;
    private String description;

    private Set<DogPictureDto> dogPictures;

    private int lifespanMin;

    private int lifespanMax;

    private BehaviourType behaviourType;

    private BarkingFrequency barkingFrequency;

    private DogSize dogSize;

    private FurType furType;
}
