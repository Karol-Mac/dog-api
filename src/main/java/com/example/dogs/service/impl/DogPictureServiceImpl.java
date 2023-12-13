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

    public DogPictureServiceImpl(DogPictureRepository dogPictureRepository,
                                 DogBreedRepository dogBreedRepository) {
        this.dogPictureRepository = dogPictureRepository;
        this.dogBreedRepository = dogBreedRepository;
    }

    @Override
    public String addDogPicture(MultipartFile image, long breedId) throws IOException {

        DogBreed dogBreed = getDogBreed(breedId);

        DogPicture dogPicture = DogPicture.builder()
                    .dogBreed(dogBreed)
                    .image(image.getBytes())
                    .build();

        dogPictureRepository.save(dogPicture);

        return "Picture "+image.getOriginalFilename()+" added successfully";
    }



    @Override
    public String addMultipleDogPictures(MultipartFile[] images, long breedId) throws IOException {
        DogBreed dogBreed = getDogBreed(breedId);

        for (MultipartFile image: images) {
            DogPicture dogPicture = DogPicture.builder()
                    .dogBreed(dogBreed)
                    .image(image.getBytes())
                    .build();

            dogPictureRepository.save(dogPicture);
        }
        return "Pictures for breed \""+ dogBreed.getName()+"\" added successfully";
    }

    @Override
    public byte[] getDogPicture(long breedId, long pictureId) {

        DogBreed dogBreed = getDogBreed(breedId);

        DogPicture dogPicture = dogPictureRepository.findById(pictureId).orElseThrow(
                () -> new DogApiException("Picture with id = " + pictureId + " not found"));

        if(dogPicture.getDogBreed().getId() != dogBreed.getId()) {
            throw new DogApiException("Picture does not belong to breed with id = " + breedId);
        }

        return dogPicture.getImage();
    }

    @Override
    public List<byte[]> getBreedPictures(long breedId) {

        List<DogPicture> dogPictures = dogPictureRepository.findAll();

        return dogPictures.stream()
                .filter(picture -> picture.getDogBreed().getId() == breedId)
                .map(DogPicture::getImage)
                .toList();
    }

    @Override
    public String deleteDogPicture(long pictureId) {
        return null;
    }

    @Override
    public byte[] updateDogPicture(long pictureId, MultipartFile image) {
        return new byte[0];
    }


    private DogBreed getDogBreed(long breedId) {
        return dogBreedRepository.findById(breedId).orElseThrow(
                () -> new DogApiException("Breed with id = " + breedId + " not found")
        );
    }
}
