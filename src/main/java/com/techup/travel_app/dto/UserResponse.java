package com.techup.travel_app.dto;

import lombok.Data;

import java.time.OffsetDateTime;


@Data
public class UserResponse {

    private Long id;
    private String email;
    private String displayName;
    private OffsetDateTime createdAt;
;
}
