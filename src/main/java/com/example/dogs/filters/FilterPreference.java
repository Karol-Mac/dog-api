package com.example.dogs.filters;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilterPreference {
    private PreferencesType preferencesType;

    private String value;
}

