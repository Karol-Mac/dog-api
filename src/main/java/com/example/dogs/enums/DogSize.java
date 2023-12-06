package com.example.dogs.enums;

import lombok.Getter;

@Getter
public enum DogSize {
    SMALL("SMALL"),
    MEDIUM("MEDIUM"),
    LARGE("LARGE");

    private final String size;

    DogSize(String size) {
        this.size = size;
    }
}

