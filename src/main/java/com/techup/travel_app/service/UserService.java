package com.techup.travel_app.service;

import com.techup.travel_app.dto.UserRequest;
import com.techup.travel_app.dto.UserResponse;
import com.techup.travel_app.entity.User;
import com.techup.travel_app.repository.UserRepository;
import com.techup.travel_app.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // ดึงข้อมูลทั้งหมด
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // ดึง user ตาม id
    public UserResponse getUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + id));
        return toResponse(user);
    }

    // อัปเดต user
    public UserResponse updateUser(Long id, UserRequest request) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + id));

        existing.setEmail(request.getEmail());
        existing.setPasswordHash(request.getPassword());
        existing.setDisplayName(request.getDisplayName());

        User saved = userRepository.save(existing);
        return toResponse(saved);
    }

    // ลบ user
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found: " + id);
        }
        userRepository.deleteById(id);
    }

    // Helper แปลง entity -> DTO
    private UserResponse toResponse(User user) {
        UserResponse resp = new UserResponse();
        resp.setId(user.getId());
        resp.setEmail(user.getEmail());
        resp.setDisplayName(user.getDisplayName());
        resp.setCreatedAt(user.getCreatedAt());
        return resp;
    }
}
