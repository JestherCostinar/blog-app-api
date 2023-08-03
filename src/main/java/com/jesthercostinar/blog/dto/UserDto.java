package com.jesthercostinar.blog.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDto {

    private int id;
    @NotEmpty
    @Size(min = 4, message = "Username must be minimum of 4 character")
    private String name;

    @Email(message = "The email address is not valid, please provide valid email")
    private String email;

    @NotEmpty
    @Size(min = 8, message = "The password must be 8 character long")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!*_\\-])[a-zA-Z0-9@#$%^&+=!*_\\-]{8,}$",
            message = "Password must be at least 8 characters long and contain at least one digit, one lowercase letter, one uppercase letter, and one special character (@#$%^&+=!*-)")
    private String password;

    @NotEmpty
    private String about;

}
