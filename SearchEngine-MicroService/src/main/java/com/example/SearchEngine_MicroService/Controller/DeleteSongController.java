package com.example.SearchEngine_MicroService.Controller;

import com.example.SearchEngine_MicroService.Service.DeleteSongService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
public class DeleteSongController {

    private final DeleteSongService deleteSongService;

    DeleteSongController(DeleteSongService deleteSongService) {
        this.deleteSongService = deleteSongService;
    }

    @PostMapping("/delete")
    public ResponseEntity<Map<String, String>> deleteSong(@RequestBody Map<String, String> map) {

        String songId = map.get("id");
        var res_ = deleteSongService.deleteSong(songId);
        return ResponseEntity.ok(res_);
    }


}

