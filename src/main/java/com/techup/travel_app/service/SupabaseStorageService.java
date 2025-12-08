package com.techup.travel_app.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Service
public class SupabaseStorageService {

  @Value("${supabase.url}")
  private String supabaseUrl;

  @Value("${supabase.bucket}")
  private String bucket;

  @Value("${supabase.apiKey}")
  private String apiKey;

  // Configuration
  private static final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 10MB
  private static final String[] ALLOWED_TYPES = {"image/jpeg", "image/png", "image/webp", "image/gif"};

  private final WebClient webClient = WebClient.builder().build();

  /**
   * Upload file to Supabase Storage with validation.
   * @param file MultipartFile to upload
   * @return Public URL of the uploaded file
   * @throws ResponseStatusException if validation fails or upload fails
   */
  public String uploadFile(MultipartFile file) {
    // Validate file
    validateFile(file);

    String original = file.getOriginalFilename() != null ? file.getOriginalFilename() : "file.bin";
    String fileName = System.currentTimeMillis() + "_" + sanitizeFileName(original);
    String uploadUrl = String.format("%s/storage/v1/object/%s/%s", supabaseUrl, bucket, fileName);

    byte[] bytes;
    try {
      bytes = file.getBytes();
    } catch (IOException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot read file bytes", e);
    }

    try {
      webClient.put()
          .uri(uploadUrl)
          .header("Authorization", "Bearer " + apiKey)     // Service Role Key
          .header("Content-Type", file.getContentType() != null ? file.getContentType() : "application/octet-stream")
          .bodyValue(bytes)
          .retrieve()
          .onStatus(HttpStatusCode::isError, res ->
              res.bodyToMono(String.class).defaultIfEmpty("Upload failed").flatMap(msg ->
                  Mono.error(new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Supabase upload failed: " + msg))
              )
          )
          .toBodilessEntity()
          .block();

      // Return public URL
      return String.format("%s/storage/v1/object/public/%s/%s", supabaseUrl, bucket, fileName);

    } catch (ResponseStatusException ex) {
      throw ex;
    } catch (Exception ex) {
      throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Unexpected error while uploading to Supabase", ex);
    }
  }

  /**
   * Validate file before upload
   */
  private void validateFile(MultipartFile file) {
    if (file == null || file.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File is empty");
    }

    if (file.getSize() > MAX_FILE_SIZE) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
          "File size exceeds maximum limit of 10MB");
    }

    String contentType = file.getContentType();
    boolean isAllowed = false;
    for (String allowed : ALLOWED_TYPES) {
      if (allowed.equals(contentType)) {
        isAllowed = true;
        break;
      }
    }

    if (!isAllowed) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
          "File type not allowed. Allowed types: JPEG, PNG, WebP, GIF");
    }
  }

  /**
   * Sanitize file name to avoid path traversal and special characters
   */
  private String sanitizeFileName(String fileName) {
    return fileName.replaceAll("[^a-zA-Z0-9._-]", "_");
  }
}