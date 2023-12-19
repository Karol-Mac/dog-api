package com.example.dogs.service;

import com.example.dogs.entity.DogPicture;
import com.example.dogs.payload.DogPictureDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface DogPictureService {

    Long addDogPicture(MultipartFile image, long breedId) throws IOException;

    List<Long> addMultipleDogPictures(MultipartFile[] images, long breedId) throws IOException;

    /**
     * @param breedId - the breed id of the dog
     * @param pictureId - the picture id of the dog
     * @return - the DogPicture object (not only photo)
     */
    DogPictureDto retrieveDogPicture(long breedId, long pictureId);


    /**
     * @param breedId - the breed id of the dog
     * @param pictureId - the picture id of the dog
     * @return - return ONLY the photo of the dog (not a whole object)
     */
    byte[] getDogPicture(long breedId, long pictureId);

    /**
     * @param breedId - the breed id of the dog
     * @return - all DogPictures (objects) of specific breed
     */
    List<DogPictureDto> getBreedPictures(long breedId);

    String deleteDogPicture(long pictureId);

    byte[] updateDogPicture(long pictureId, MultipartFile image) throws IOException;
}
