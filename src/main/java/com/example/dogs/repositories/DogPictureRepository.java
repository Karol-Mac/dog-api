package com.example.dogs.repositories;

import com.example.dogs.entity.DogPicture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DogPictureRepository extends JpaRepository<DogPicture, Long> {

}
