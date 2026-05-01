package com.example.MIcroService_Player.Controller;


import com.example.MIcroService_Player.Service.RetrievalService;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SongController {

    private final RetrievalService retrievalService;


    public SongController(RetrievalService retrievalService) {
        this.retrievalService = retrievalService;
    }

    @GetMapping({"/get/{songid}"})
    public ResponseEntity<Resource> getSong(@PathVariable("songid") String songid, @RequestHeader(value = "Range", required = false) String range) {

        return retrievalService.getSong(songid, range);
    }
}
