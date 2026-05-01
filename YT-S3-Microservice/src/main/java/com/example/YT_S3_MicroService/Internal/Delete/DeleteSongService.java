package com.example.YT_S3_MicroService.Internal.Delete;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;

@Service
// Package Private
class DeleteSongService {

    private final S3AsyncClient s3Client;

    @Value("${aws.bucket}")
    String bucket;

    public DeleteSongService(S3AsyncClient s3Client) {
        this.s3Client = s3Client;
    }

    public boolean deleteSong(String path) {
        try {
            s3Client.deleteObject(
                    DeleteObjectRequest.builder()
                            .bucket(bucket)
                            .key(path)
                            .build()
            ).join();

            return true; // success if no exception
        } catch (Exception e) {
            return false;
        }
    }
}
