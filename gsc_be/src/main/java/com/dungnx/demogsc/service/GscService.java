package com.dungnx.demogsc.service;

import com.dungnx.demogsc.entity.Image;
import com.dungnx.demogsc.repository.ImageRepository;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class GscService {
    private final Storage storage;
    private final ImageRepository imageRepository;

    @Value("${gcs.bucket.name}")
    private String bucketName;

    GscService(Storage storage, ImageRepository imageRepository) {
        this.storage = storage;
        this.imageRepository = imageRepository;
    }

    public String uploadFile(MultipartFile file) {
        String objectName = UUID.randomUUID().toString();
        BlobId blobId = BlobId.of(bucketName, objectName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();

        try {
            storage.create(blobInfo, file.getBytes());

            String fileUrl = String.format("https://storage.googleapis.com/%s/%s", bucketName, objectName);

            // Save file URL to database
            Image image = new Image();
            image.setImageUrl(fileUrl);
            imageRepository.save(image);

            return fileUrl;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
