package com.example.dogs.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// FIXME: czy trzeba dodać jakieś pole, żeby powiązać z odpowiednią dogBreed?
// TODO: utwórz DogPictures service i controller
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DogPictureDto {

    private long id;
    private byte[] image;
}
