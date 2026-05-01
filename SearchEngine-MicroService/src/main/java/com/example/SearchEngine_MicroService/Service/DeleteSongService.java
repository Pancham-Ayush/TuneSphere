package com.example.SearchEngine_MicroService.Service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import com.example.SearchEngine_MicroService.Fegin.S3DeleteReq;
import com.example.SearchEngine_MicroService.Model.Song;
import com.example.SearchEngine_MicroService.Repo.PlaylistRepo;
import com.example.SearchEngine_MicroService.Repo.SongRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class DeleteSongService {

    private final SongRepo songRepo;

    private final PlaylistRepo playlistRepo;

    private ElasticsearchClient elasticsearchClient;

    private S3DeleteReq s3DeleteReq;

    @Value("${elasticsearch.index}")
    private String indexName;

    public DeleteSongService(SongRepo songRepo, PlaylistRepo playlistRepo, ElasticsearchClient elasticsearchClient, S3DeleteReq s3DeleteReq) {
        this.songRepo = songRepo;
        this.playlistRepo = playlistRepo;
        this.elasticsearchClient = elasticsearchClient;
        this.s3DeleteReq = s3DeleteReq;
    }

    public Map<String, String> deleteSong(String songId) {
        Song song = songRepo.findById(songId).get();

        if (song == null)
            return Map.of("Message", "Song not found");

        String path = song.getPath();

        if (!s3DeleteReq.delete(path))
            return Map.of("Message", "Song could not be deleted");

        songRepo.deleteSong(song.getId());

        playlistRepo.deleteFromPlaylist(song.getId());

        try {
            elasticsearchClient.delete(d -> d.index(indexName).id(songId));

        } catch (IOException e) {
            e.printStackTrace();
        }

        return Map.of("Message", "Song deleted successfully!");


    }

}
