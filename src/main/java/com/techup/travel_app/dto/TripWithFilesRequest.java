package com.techup.travel_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TripWithFilesRequest {

    @NotBlank
    private String title;

    private String description;

    private List<MultipartFile> files;

    private List<String> tags;

    private Double latitude;

    private Double longitude;

    private Long authorId;
}
