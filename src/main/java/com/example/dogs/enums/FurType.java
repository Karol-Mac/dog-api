package com.example.dogs.enums;

import lombok.Getter;

@Getter
public enum FurType {
    SHORT("SHORT"),
    MEDIUM("MEDIUM"),
    LONG("LONG"),
    HAIR("HAIR"),
    HAIRLESS("HAIRLESS");

    private final String fur;

    FurType(String fur) {
        this.fur = fur;
    }
}
