package com.example.dogs.enums;

import lombok.Getter;

@Getter
public enum BarkingFrequency {
    LOW("LOW"),
    MODERATE("MODERATE"),
    HIGH("HIGH");

    private final String frequency;

    BarkingFrequency(String frequency) {
        this.frequency = frequency;
    }
}

