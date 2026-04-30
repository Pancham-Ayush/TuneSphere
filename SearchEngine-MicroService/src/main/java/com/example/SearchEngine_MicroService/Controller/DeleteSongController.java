package com.example.SearchEngine_MicroService.Controller;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.DeleteRequest;
import com.example.SearchEngine_MicroService.Model.Song;
import com.example.SearchEngine_MicroService.Repo.PlaylistRepo;
import com.example.SearchEngine_MicroService.Repo.SongRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
public class DeleteSongController {

    private final SongRepo songRepo;

    private final PlaylistRepo playlistRepo;

    private final S3Client s3Client;

    private ElasticsearchClient elasticsearchClient;

    @Value("${aws.bucket}")
    private String bucketName;

    @Value("${elasticsearch.index}")
    private String indexName;

    DeleteSongController(SongRepo songRepo, PlaylistRepo playlistRepo, S3Client s3Client, ElasticsearchClient elasticsearchClient) {
        this.songRepo = songRepo;
        this.playlistRepo = playlistRepo;
        this.s3Client = s3Client;
        this.elasticsearchClient = elasticsearchClient;
    }

    @PostMapping("/delete")
    public ResponseEntity<Map<String, String>> deleteSong(@RequestBody Map<String, String> map) {

        Song song = songRepo.findById(map.get("id")).orElse(null);
        if (song == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("Message", "Song not found"));
        }

        String key = song.getPath();

        songRepo.deleteSong(song.getId());

        playlistRepo.deleteFromPlaylist(song.getId());

        DeleteRequest request = new DeleteRequest
                .Builder()
                .index(indexName)
                .id(song.getId())
                .build();

        try {
            elasticsearchClient.delete(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        CompletableFuture.runAsync(() -> {
            try {
                s3Client.deleteObject(DeleteObjectRequest.builder()
                        .bucket(bucketName)
                        .key(key)
                        .build());
            } catch (Exception e) {
                log.error("Exception occurred while trying to delete song {}", key, e);
            }
        });

        return ResponseEntity.ok(Map.of("Message", "Song deleted successfully!"));
    }


}

