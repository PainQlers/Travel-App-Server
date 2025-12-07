package com.techup.travel_app.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import com.techup.travel_app.service.SupabaseStorageService;

@RestController
@RequestMapping("/api/files")
public class SupabaseStorageController {

    private final SupabaseStorageService storageService;

    public SupabaseStorageController(SupabaseStorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {
        String url = storageService.uploadFile(file);
        return ResponseEntity.ok(url);
    }
}

