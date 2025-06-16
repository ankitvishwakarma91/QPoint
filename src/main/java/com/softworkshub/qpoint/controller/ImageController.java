package com.softworkshub.qpoint.controller;

import com.softworkshub.qpoint.service.ImageUploadService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    @Autowired
    private ImageUploadService imageUploadService;

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {
        try {
            String imageUrl = imageUploadService.uploadImage(file);
            return ResponseEntity.ok(imageUrl);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error uploading image: " + e.getMessage());
        }
    }
}
