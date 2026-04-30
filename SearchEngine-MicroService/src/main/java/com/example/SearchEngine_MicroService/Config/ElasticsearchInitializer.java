package com.example.SearchEngine_MicroService.Config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ElasticsearchInitializer {

    private final ElasticsearchClient client;

    @Value("${elasticsearch.index}")
    private String index;

    public ElasticsearchInitializer(ElasticsearchClient client) {
        this.client = client;
    }

    @PostConstruct
    public void init() {
        try {
            boolean exists = client.indices()
                    .exists(e -> e.index(index))
                    .value();

            if (!exists) {
                client.indices().create(c -> c.index(index));
                System.out.println("Index created: " + index);
            } else {
                System.out.println("Index already exists");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}