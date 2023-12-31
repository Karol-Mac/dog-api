package com.example.dogs.payload;

import com.example.dogs.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class RegisterDto {
    private long id;

    private String username;

    private String email;

    private String password;

    private Role role;              //???
}
