package com.techup.travel_app.controller;

import com.techup.travel_app.dto.TripResponse;
import com.techup.travel_app.dto.TripRequest;
import com.techup.travel_app.dto.TripWithFilesRequest;
import com.techup.travel_app.service.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/trips")
@RequiredArgsConstructor
public class TripController {

    private final TripService tripService;

    @GetMapping
    public List<TripResponse> getAll() {
        return tripService.getAllTrips();
    }

    @GetMapping("/{id}")
    public TripResponse getById(@PathVariable Long id) {
        return tripService.getTrip(id);
    }

    @PostMapping(consumes = "application/json")
    public TripResponse create(@RequestBody TripRequest request) {
        return tripService.createTrip(request);
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public TripResponse create(
            @RequestParam("title") String title,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "files", required = false) List<MultipartFile> files,
            @RequestParam(value = "tags", required = false) List<String> tags,
            @RequestParam(value = "latitude", required = false) Double latitude,
            @RequestParam(value = "longitude", required = false) Double longitude,
            @RequestParam(value = "authorId", required = false) Long authorId) {

        TripWithFilesRequest request = new TripWithFilesRequest();
        request.setTitle(title);
        request.setDescription(description);
        request.setFiles(files);
        request.setTags(tags);
        request.setLatitude(latitude);
        request.setLongitude(longitude);
        request.setAuthorId(authorId);

        return tripService.createTripWithFiles(request);
    }

    @PutMapping("/{id}")
    public TripResponse update(@PathVariable Long id, @RequestBody TripRequest trip) {
        return tripService.updateTrip(id, trip);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        tripService.deleteTrip(id);
        return ResponseEntity.ok().build();
    }

    // removed duplicate /with-files endpoint — handled by multipart POST above
}
