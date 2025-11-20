package com.techup.travel_app.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String password;
}

