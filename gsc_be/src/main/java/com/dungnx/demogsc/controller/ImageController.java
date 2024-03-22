package com.dungnx.demogsc.controller;

import com.dungnx.demogsc.config.MakeBucketPublic;
import com.dungnx.demogsc.service.GscService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ImageController {

    private final GscService gcsService;

    @Autowired
    public ImageController(GscService gcsService) {
        this.gcsService = gcsService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        MakeBucketPublic.makeBucketPublic("${gcs.project-id}", "upload-file2");
        try {
            String fileUrl = gcsService.uploadFile(file);
            return ResponseEntity.ok().body("File uploaded successfully. File URL: " + fileUrl);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to upload file. Error: " + e.getMessage());
        }
    }
}
