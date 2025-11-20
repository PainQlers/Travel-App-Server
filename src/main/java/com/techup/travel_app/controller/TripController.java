package com.techup.travel_app.controller;

import com.techup.travel_app.dto.TripResponse;
import com.techup.travel_app.dto.TripRequest;
import com.techup.travel_app.service.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public TripResponse create(@RequestBody TripRequest request) {
        return tripService.createTrip(request);
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
}
