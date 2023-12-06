package com.example.dogs.entity;

import com.example.dogs.enums.BarkingFrequency;
import com.example.dogs.enums.BehaviourType;
import com.example.dogs.enums.DogSize;
import com.example.dogs.enums.FurType;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "dog_breeds")
public class DogBreed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true, length = 50)
    private String name;

    @Column(nullable = false)
    private String description;

    private int lifespanMin;

    private int lifespanMax;

    @Enumerated(EnumType.STRING)
    private BehaviourType behaviourType;

    @Enumerated(EnumType.STRING)
    private BarkingFrequency barkingFrequency;

    @Enumerated(EnumType.STRING)
    private DogSize dogSize;

    @Enumerated(EnumType.STRING)
    private FurType furType;

    @OneToMany(mappedBy = "dogBreed",
            cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DogPicture> dogPictures;
}