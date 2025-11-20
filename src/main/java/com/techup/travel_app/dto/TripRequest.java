package com.techup.travel_app.dto;

import lombok.AllArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TripRequest {

    @NotBlank
    private String title;

    private String description;

    private List<String> photos;

    private List<String> tags;

    private Double latitude;

    private Double longitude;

    private Long authorId;
}
