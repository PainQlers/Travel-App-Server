package com.techup.travel_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.techup.travel_app.entity.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{
        Optional<User> findByEmail(String email);
}
