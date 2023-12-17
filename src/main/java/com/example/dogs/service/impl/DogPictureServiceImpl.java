package com.example.dogs.service.impl;

import com.example.dogs.entity.DogBreed;
import com.example.dogs.entity.DogPicture;
import com.example.dogs.exceptions.DogApiException;
import com.example.dogs.payload.DogBreedDto;
import com.example.dogs.payload.DogPictureDto;
import com.example.dogs.repositories.DogBreedRepository;
import com.example.dogs.repositories.DogPictureRepository;
import com.example.dogs.service.DogPictureService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
        List<Long> ids;
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
    public DogPictureDto retrieveDogPicture(long breedId, long pictureId) {
        DogPicture dogPicture = getDogPicture(pictureId);
        DogBreed dogBreed = getDogBreed(breedId);

        if(dogPicture.getDogBreed().getId() != dogBreed.getId())
            throw new DogApiException("Picture does not belong to breed with id = " + breedId);

        return mapper.map(dogPicture, DogPictureDto.class);
    }

    @Override
    public byte[] getDogPicture(long breedId, long pictureId){

        DogBreed dogBreed = getDogBreed(breedId);

        DogPicture dogPicture = getDogPicture(pictureId);

        if(dogPicture.getDogBreed().getId() != dogBreed.getId()) {
            throw new DogApiException("Picture does not belong to breed with id = " + breedId);
        }

        return dogPicture.getImage();
    }



    @Override
    public List<DogPictureDto> getBreedPictures(long breedId) {

        List<DogPicture> dogPictures = dogPictureRepository.findAll();

        return dogPictures.stream()
                .filter(picture -> picture.getDogBreed().getId() == breedId)
                .map(dogPicture -> mapper.map(dogPicture, DogPictureDto.class))
                .toList();
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

    private DogBreed getDogBreed(long breedId) {
        return dogBreedRepository.findById(breedId).orElseThrow(
                () -> new DogApiException("Breed with id = " + breedId + " not found")
        );
    }

    private DogPicture getDogPicture(long pictureId) {
        return dogPictureRepository.findById(pictureId).orElseThrow(
                () -> new DogApiException("Picture with id = " + pictureId + " not found"));
    }
}
