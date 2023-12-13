package com.example.dogs.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface DogPictureService {

    String addDogPicture(MultipartFile image, long breedId) throws IOException;

    String addMultipleDogPictures(MultipartFile[] images, long breedId) throws IOException;


    byte[] getDogPicture(long breedId, long pictureId);

    List<byte[]> getBreedPictures(long breedId);

    String deleteDogPicture(long pictureId);

    byte[] updateDogPicture(long pictureId, MultipartFile image);

}
