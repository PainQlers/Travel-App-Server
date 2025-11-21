package com.techup.travel_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.OffsetDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class TripResponse {
    private Long id;
    private String title;
    private String description;
    private List<String> photos;
    private List<String> tags;
    private Double latitude;
    private Double longitude;
    private Long authorId;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}

