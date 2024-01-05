package com.example.dogs.enums;

import lombok.Getter;

@Getter
public enum BehaviourType {
    FRIENDLY("FRIENDLY"),
    RESERVED("RESERVED"),
    AGGRESSIVE("AGGRESSIVE");

    private final String behavior;

    BehaviourType(String behavior) {
        this.behavior = behavior;
    }
}