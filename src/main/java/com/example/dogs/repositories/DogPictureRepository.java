package com.example.dogs.repositories;

import com.example.dogs.entity.DogPicture;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface DogPictureRepository extends JpaRepository<DogPicture, Long> {
    Optional<DogPicture> findByFileName(String fileName);
}
