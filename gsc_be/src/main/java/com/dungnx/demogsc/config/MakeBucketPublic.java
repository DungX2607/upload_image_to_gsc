package com.dungnx.demogsc.config;

import com.google.cloud.Identity;
import com.google.cloud.Policy;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.cloud.storage.StorageRoles;

public class MakeBucketPublic {
    public static void makeBucketPublic(String projectId, String bucketName) {
        // The ID of your GCP project
        // String projectId = "your-project-id";

        // The ID of your GCS bucket
        // String bucketName = "your-unique-bucket-name";

        Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
        Policy originalPolicy = storage.getIamPolicy(bucketName);
        storage.setIamPolicy(
                bucketName,
                originalPolicy
                        .toBuilder()
                        .addIdentity(StorageRoles.objectViewer(), Identity.allUsers()) // All users can view
                        .build());

        System.out.println("Bucket " + bucketName + " is now publicly readable");
    }
}