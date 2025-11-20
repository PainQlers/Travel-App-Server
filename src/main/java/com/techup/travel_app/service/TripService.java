package com.techup.travel_app.service;

import com.techup.travel_app.entity.Trip;
import com.techup.travel_app.entity.User;
import com.techup.travel_app.repository.TripRepository;
import com.techup.travel_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.techup.travel_app.dto.TripRequest;
import com.techup.travel_app.dto.TripResponse;
import com.techup.travel_app.exception.ResourceNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TripService {

    private final TripRepository tripRepository;
    private final UserRepository userRepository;

    public List<TripResponse> getAllTrips() {
        return tripRepository.findAll().stream()
            .map(trip -> new TripResponse(
                trip.getId(),
                trip.getTitle(),
                trip.getDescription(),
                trip.getPhotos(),
                trip.getTags(),
                trip.getLatitude(),
                trip.getLongitude(),
                trip.getAuthor() != null ? trip.getAuthor().getId() : null,
                trip.getCreatedAt(),
                trip.getUpdatedAt()
            ))
            .toList();
    }

    public TripResponse getTrip(Long id) {
        Trip trip = tripRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Trip not found: " + id));
    
        return new TripResponse(
                trip.getId(),
                trip.getTitle(),
                trip.getDescription(),
                trip.getPhotos(),
                trip.getTags(),
                trip.getLatitude(),
                trip.getLongitude(),
                trip.getAuthor() != null ? trip.getAuthor().getId() : null,
                trip.getCreatedAt(),
                trip.getUpdatedAt()
        );
    }

    public TripResponse createTrip(TripRequest request) {
        Trip trip = new Trip();
        trip.setTitle(request.getTitle());
        trip.setDescription(request.getDescription());
        trip.setPhotos(request.getPhotos());
        trip.setTags(request.getTags());
        trip.setLatitude(request.getLatitude());
        trip.setLongitude(request.getLongitude());
    
        if (request.getAuthorId() != null) {
            User user = userRepository.findById(request.getAuthorId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found: " + request.getAuthorId()));
            trip.setAuthor(user);
        }
    
        Trip saved = tripRepository.save(trip);
        return new TripResponse(
                saved.getId(),
                saved.getTitle(),
                saved.getDescription(),
                saved.getPhotos(),
                saved.getTags(),
                saved.getLatitude(),
                saved.getLongitude(),
                saved.getAuthor() != null ? saved.getAuthor().getId() : null,
                saved.getCreatedAt(),
                saved.getUpdatedAt()
        );
    }
    
    private Trip findTripById(Long id) {
        return tripRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Trip not found: " + id));
    }

    public TripResponse updateTrip(Long id, TripRequest request) {
        Trip existing = findTripById(id);
    
        existing.setTitle(request.getTitle());
        existing.setDescription(request.getDescription());
        existing.setPhotos(request.getPhotos());
        existing.setTags(request.getTags());
        existing.setLatitude(request.getLatitude());
        existing.setLongitude(request.getLongitude());
    
        if (request.getAuthorId() != null) {
            User user = userRepository.findById(request.getAuthorId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found: " + request.getAuthorId()));
            existing.setAuthor(user);
        }
    
        Trip saved = tripRepository.save(existing);
    
        return new TripResponse(
                saved.getId(),
                saved.getTitle(),
                saved.getDescription(),
                saved.getPhotos(),
                saved.getTags(),
                saved.getLatitude(),
                saved.getLongitude(),
                saved.getAuthor() != null ? saved.getAuthor().getId() : null,
                saved.getCreatedAt(),
                saved.getUpdatedAt()
        );
    }
    

    public void deleteTrip(Long id) {
        tripRepository.deleteById(id);
    }
}
