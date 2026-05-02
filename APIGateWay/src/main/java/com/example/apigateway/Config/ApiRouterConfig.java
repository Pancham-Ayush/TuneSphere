package com.example.apigateway.Config;

import com.example.apigateway.Filter.AdminCheckFilter;
import com.example.apigateway.Filter.JwtCookieFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiRouterConfig {

    private AdminCheckFilter adminCheckFilter;

    ApiRouterConfig(AdminCheckFilter adminCheckFilter) {
        this.adminCheckFilter = adminCheckFilter;
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder, JwtCookieFilter jwtFilter) {
        return builder.routes()
                .route("ai-agent", r -> r
                                .path("/chat/**")
                                .filters(f ->
                                                f.stripPrefix(1)
//                                        .filter(jwtFilter)
                                                        .circuitBreaker(c -> c
                                                                .setName("ai-agent")
                                                                .setFallbackUri("forward:/fallback/ai")
                                                        )
                                )
                                .uri("lb://Orchestrator")
                )
//                -------------------------------------------------
                .route("song-player", r -> r
                        .path("/play/**")
                        .filters(f -> f
                                .stripPrefix(1)
                                .filter(jwtFilter)
                                .circuitBreaker(c -> c
                                        .setName("song-player")
                                        .setFallbackUri("forward:/fallback/player")
                                )
                        )
                        .uri("lb://PLAYER-SERVICE"))
//                ---------------------------------------------------
                .route("search", r -> r
                        .path("/search/**")
                        .filters(f -> f
                                        .stripPrefix(1)
//                                .filter(jwtFilter)
                                        .circuitBreaker(c -> c
                                                .setName("search")
                                                .setFallbackUri("forward:/fallback/search")
                                        )
                        )
                        .uri("lb://SEARCHENGINE-MICROSERVICE"))
//                ---------------------------------------------------
                .route("ai-service-route", r -> r
                        .path("/yt-ai/**")
                        .filters(f -> f
                                .stripPrefix(1)
                                .filter(jwtFilter)
                                .circuitBreaker(c -> c
                                        .setName("yt-ai")
                                        .setFallbackUri("forward:/fallback/yt")
                                )
                        )
                        .uri("lb://YT-AI"))
//                ---------------------------------------------------
                .route("security", r -> r
                        .path("/auth/**")
                        .filters(f -> f
                                .stripPrefix(1)
                                .circuitBreaker(c -> c
                                        .setName("security")
                                        .setFallbackUri("forward:/fallback/security")
                                )
                        )
                        .uri("lb://SECURITY-MICROSERVICE"))
                //                --------------------------------------------------
                .route("s3", r -> r
                        .path("/s3/upload/notification/stream")
                        .filters(f -> f
                                .stripPrefix(2)
                                .filter(jwtFilter)
                                .circuitBreaker(c -> c
                                        .setName("s3")
                                        .setFallbackUri("forward:/fallback/s3")))
                        .uri("lb://YT-S3-MICROSERVICE"))
                //                -----------------------------------------------
                .route("admin-upload", r -> r
                        .path("/s3/upload/**")
                        .filters(f -> f
                                .stripPrefix(1)
                                .filter(adminCheckFilter)
                                .circuitBreaker(c -> c
                                        .setName("s3")
                                        .setFallbackUri("forward:/fallback/s3")))
                        .uri("lb://YT-S3-MICROSERVICE")
                )
//                ---------------------------------------------------
                .route("admin-delete", r -> r
                        .path("/s3/delete/**")
                        .filters(f -> f
                                .stripPrefix(1)
                                .filter(adminCheckFilter)
                                .circuitBreaker(c -> c
                                        .setName("s3")
                                        .setFallbackUri("forward:/fallback/s3")
                                )
                        )
                        .uri("lb://SEARCHENGINE-MICROSERVICE"))
                .build();
    }
}
