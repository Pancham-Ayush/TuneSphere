package com.example.apigateway.FallBack;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/fallback")
class FallbackController {

    @RequestMapping("/player")
    public ResponseEntity<Map<String, Object>> playerFallback() {
        return buildResponse("PLAYER-SERVICE", "Player service is down");
    }

    @RequestMapping("/security")
    public ResponseEntity<Map<String, Object>> securityrFallback() {
        return buildResponse("SECURITY-SERVICE", "Security service is down");
    }

    @RequestMapping("/search")
    public ResponseEntity<Map<String, Object>> searchFallback() {
        return buildResponse("SEARCH-SERVICE", "Search service is down");
    }

    @RequestMapping("/ai")
    public ResponseEntity<Map<String, Object>> aiFallback() {
        return buildResponse("AI-SERVICE", "AI service is down");
    }

    @RequestMapping("/yt")
    public ResponseEntity<Map<String, Object>> ytFallback() {
        return buildResponse("YT-SERVICE", "YT service is down");
    }

    @RequestMapping("/s3")
    public ResponseEntity<Map<String, Object>> s3Fallback() {
        return buildResponse("S3-SERVICE", "S3 service SSE down");
    }

    private ResponseEntity<Map<String, Object>> buildResponse(String service,
                                                              String message) {

        Map<String, Object> body = new HashMap<>();
        body.put("service", service);
        body.put("message", message);
        body.put("status", 503);
        body.put("timestamp", java.time.Instant.now().toString());

        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(body);
    }
}