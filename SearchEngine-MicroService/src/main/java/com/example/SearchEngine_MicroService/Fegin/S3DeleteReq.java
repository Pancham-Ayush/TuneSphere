package com.example.SearchEngine_MicroService.Fegin;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "YT-S3-Microservice")
public interface S3DeleteReq {

    @DeleteMapping("/internal/delete")
    public boolean delete(@RequestParam("id") String path);
}
