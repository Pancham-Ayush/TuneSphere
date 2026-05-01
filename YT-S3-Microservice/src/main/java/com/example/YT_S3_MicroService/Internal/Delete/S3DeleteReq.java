package com.example.YT_S3_MicroService.Internal.Delete;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal")
public class S3DeleteReq {


    private final DeleteSongService deleteSongService;

    public S3DeleteReq(DeleteSongService deleteSongService) {
        this.deleteSongService = deleteSongService;
    }

    @DeleteMapping("/delete")
    public boolean deleteSong(@RequestParam("id") String path) {
        return deleteSongService.deleteSong(path);
    }

}
