package com.example.dogs.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DogPictureDtoByte {

    private long id;

    private byte[] image;

    private String fileName;
}
