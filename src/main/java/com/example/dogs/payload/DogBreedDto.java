package com.example.dogs.payload;

import com.example.dogs.enums.BarkingFrequency;
import com.example.dogs.enums.BehaviourType;
import com.example.dogs.enums.DogSize;
import com.example.dogs.enums.FurType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DogBreedDto {
    private long id;

    @Size(max = 41, min = 9, message = "breed name need to have at least 5 characters")
    private String name;
    private String description;

    @NotNull
    @NotEmpty(message = "the minimum age must not be empty")
    private int lifespanMin;

    @NotNull
    @NotEmpty(message = "the maximum age must not be empty")
    private int lifespanMax;

    @NotNull
    private BehaviourType behaviourType;

    @NotNull
    private BarkingFrequency barkingFrequency;

    @NotNull
    private DogSize dogSize;

    @NotNull
    private FurType furType;
}
