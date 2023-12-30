package com.example.dogs.service.impl;

import com.example.dogs.entity.DogBreed;
import com.example.dogs.entity.DogPicture;
import com.example.dogs.exception.DogApiBadRequestException;
import com.example.dogs.payload.DogPictureDtoByte;
import com.example.dogs.payload.DogPictureDtoString;
import com.example.dogs.repositories.DogBreedRepository;
import com.example.dogs.repositories.DogPictureRepository;
import com.example.dogs.service.DogPictureService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Service
public class DogPictureServiceImpl implements DogPictureService {

    private final DogPictureRepository dogPictureRepository;

    private final DogBreedRepository dogBreedRepository;

    private final ModelMapper mapper;

    public DogPictureServiceImpl(DogPictureRepository dogPictureRepository,
                                 DogBreedRepository dogBreedRepository, ModelMapper mapper) {
        this.dogPictureRepository = dogPictureRepository;
        this.dogBreedRepository = dogBreedRepository;
        this.mapper = mapper;
    }

    @Override
    public Long addDogPicture(MultipartFile image, long breedId) throws IOException {

        DogBreed dogBreed = getDogBreed(breedId);

        DogPicture dogPicture = DogPicture.builder()
                    .dogBreed(dogBreed)
                    .image(image.getBytes())
                    .fileName(image.getOriginalFilename())
                    .build();

        dogPictureRepository.save(dogPicture);

        return dogPicture.getId();
    }

    @Override
    public List<Long> addMultipleDogPictures(MultipartFile[] images,
                                         long breedId) throws IOException {
        DogBreed dogBreed = getDogBreed(breedId);
        for (MultipartFile image: images) {
            DogPicture dogPicture = DogPicture.builder()
                    .dogBreed(dogBreed)
                    .image(image.getBytes())
                    .fileName(image.getOriginalFilename())
                    .build();

            dogPictureRepository.save(dogPicture);
        }

        List<DogPicture> dogPictures = dogPictureRepository.findAll();

        return dogPictures.stream()
                .filter(picture -> picture.getDogBreed().getId() == breedId)
                .map(DogPicture::getId)
                .toList();
    }

    @Override
    public DogPictureDtoByte retrieveDogPicture(long breedId, long pictureId) {
        DogPicture dogPicture = getDogPicture(pictureId);
        DogBreed dogBreed = getDogBreed(breedId);

        return mapper.map(dogPicture, DogPictureDtoByte.class);
    }

    @Override
    public byte[] getDogPicture(long breedId, long pictureId){

        DogBreed dogBreed = getDogBreed(breedId);

        DogPicture dogPicture = getDogPicture(pictureId);

        return dogPicture.getImage();
    }


    // get all pictures related to given dog breed - as (normal) byte[]
    @Override
    public List<DogPictureDtoByte> getBreedPicturesByte(long breedId) {

        List<DogPicture> dogPictures = dogPictureRepository.findAll();

        return dogPictures.stream()
                .filter(picture -> picture.getDogBreed().getId() == breedId)
                .map(dogPicture -> {

                    DogPictureDtoByte picture = new DogPictureDtoByte();

                    picture.setId(dogPicture.getId());
                    picture.setFileName(dogPicture.getFileName());
                    picture.setImage(dogPicture.getImage());
                    return picture;
                })
                .toList();
    }

    // get all pictures related to given dog breed - as base64 encoded string
    @Override
    public List<DogPictureDtoString> getBreedPicturesString(long breedId) {
        List<DogPicture> dogPictures = dogPictureRepository.findAll();

        return dogPictures.stream()
                .filter(picture -> picture.getDogBreed().getId() == breedId)
                .map(dogPicture -> {
                    String encodedImage = Base64.getEncoder().encodeToString(dogPicture.getImage());

                    DogPictureDtoString picture = new DogPictureDtoString();
                    picture.setId(dogPicture.getId());
                    picture.setFileName(dogPicture.getFileName());
                    picture.setImage(encodedImage);
                    return picture;
                }).toList();
    }

    @Override
    public byte[] updateDogPicture(long pictureId, MultipartFile image) throws IOException {
        DogPicture dogPicture = getDogPicture(pictureId);

        dogPicture.setFileName(image.getOriginalFilename());
        dogPicture.setImage(image.getBytes());

        dogPictureRepository.save(dogPicture);

        return dogPicture.getImage();
    }

    @Override
    public String deleteDogPicture(long pictureId) {
        DogPicture dogPicture = getDogPicture(pictureId);

        dogPictureRepository.delete(dogPicture);

        return "Picture "+dogPicture.getFileName()+" deleted successfully";
    }

    @Override
    public String deleteAllDogPictures(long breedId) {
        DogBreed dogBreed = getDogBreed(breedId);

        List<DogPicture> pictures = dogPictureRepository.findAll().stream()
                .filter(picture -> picture.getDogBreed().equals(dogBreed)).toList();

        dogPictureRepository.deleteAll(pictures);

        return "Successfully removed all pictures from dog breed id = "+breedId;
    }

    private DogBreed getDogBreed(long breedId) {
        return dogBreedRepository.findById(breedId).orElseThrow(
                () -> new DogApiBadRequestException(HttpStatus.BAD_REQUEST,"Breed with id = " + breedId + " not found")
        );
    }

    private DogPicture getDogPicture(long pictureId) {
        return dogPictureRepository.findById(pictureId).orElseThrow(
                () -> new DogApiBadRequestException(HttpStatus.BAD_REQUEST, "Picture with id = " + pictureId + " not found"));
    }
}
