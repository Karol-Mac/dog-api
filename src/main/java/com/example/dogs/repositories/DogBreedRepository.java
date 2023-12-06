package com.example.dogs.repositories;

import com.example.dogs.entity.DogBreed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DogBreedRepository extends JpaRepository<DogBreed, Long> {
    DogBreed findByName(String name);
}
