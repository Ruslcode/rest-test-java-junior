package com.clear.solutions.source.entities;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDTO {
    private Long userID;
    @NotBlank(message = "Required email!")
    private String email;
    @NotBlank(message = "Required firstname!")
    private String firstName;
    @NotBlank(message = "Required surname!")
    private String lastName;
    @NotBlank(message = "Required birthdate!")
    private LocalDate birthdate;
    private String address;
    private String phoneNumber;

    public UserDTO(String email, String firstName, String lastName, LocalDate birthdate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthdate = birthdate;
    }
}
