package com.example.dogs.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "dog_pictures")
public class DogPicture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // annotation that "tells" JPA that this column will have big data, like pictures
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] image;

    @ManyToOne
    @JoinColumn(name = "dog_breeds_id")
    private DogBreed dogBreed;
}