package com.techup.travel_app.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRequest {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @Size(max = 100)
    private String displayName;
}
