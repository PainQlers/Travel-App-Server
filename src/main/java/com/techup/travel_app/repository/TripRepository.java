package com.techup.travel_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.techup.travel_app.entity.Trip;

public interface TripRepository extends JpaRepository<Trip, Long>{
    
}
