package com.example.dogs.service.impl;

import com.example.dogs.entity.DogBreed;
import com.example.dogs.entity.DogPicture;
import com.example.dogs.exceptions.DogApiException;
import com.example.dogs.repositories.DogBreedRepository;
import com.example.dogs.repositories.DogPictureRepository;
import com.example.dogs.service.DogPictureService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class DogPictureServiceImpl implements DogPictureService {

    private final DogPictureRepository dogPictureRepository;

    private final DogBreedRepository dogBreedRepository;

    public DogPictureServiceImpl(DogPictureRepository dogPictureRepository, DogBreedRepository dogBreedRepository) {
        this.dogPictureRepository = dogPictureRepository;
        this.dogBreedRepository = dogBreedRepository;
    }

    @Override
    public byte[] addDogPicture(MultipartFile image, long breedId) throws IOException {

        DogBreed dogBreed = dogBreedRepository.findById(breedId).orElseThrow(
                () -> new DogApiException("Breed with id = " + breedId + " not found")
        );

            DogPicture dogPicture = DogPicture.builder()
                    .dogBreed(dogBreed)
                    .image(image.getBytes())
                    .build();

        dogPictureRepository.save(dogPicture);

        return dogPicture.getImage();
    }

    @Override
    public byte[] getDogPicture(long pictureId) {
        DogPicture dogPicture = dogPictureRepository.findById(pictureId).orElseThrow(
                () -> new DogApiException("Picture with id = " + pictureId + " not found"));

        return dogPicture.getImage();
    }

    @Override
    public List<byte[]> getBreedPictures(long breedId) {
        return null;
    }

    @Override
    public String deleteDogPicture(long pictureId) {
        return null;
    }

    @Override
    public byte[] updateDogPicture(long pictureId, MultipartFile image) {
        return new byte[0];
    }
}
