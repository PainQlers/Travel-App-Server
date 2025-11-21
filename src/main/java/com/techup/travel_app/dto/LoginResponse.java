package com.techup.travel_app.dto;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String token;
}
