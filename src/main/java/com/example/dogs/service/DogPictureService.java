package com.example.dogs.service;

import com.example.dogs.payload.DogPictureDtoByte;
import com.example.dogs.payload.DogPictureDtoString;
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
    DogPictureDtoByte retrieveDogPicture(long breedId, long pictureId);


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
    List<DogPictureDtoByte> getBreedPicturesByte(long breedId);

    List<DogPictureDtoString> getBreedPicturesString(long breedId);

    String deleteDogPicture(long pictureId);

    String deleteAllDogPictures(long pictureId);


    byte[] updateDogPicture(long pictureId, MultipartFile image) throws IOException;
}
