package com.example.SearchEngine_MicroService.Config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticSearchConfig {

    @Value("${elasticsearch.host}")
    private String host;

    @Value("${elasticsearch.port}")
    private int port;

    private ElasticsearchClient client;

    @Bean
    public ElasticsearchClient elasticsearchClient() {

        RestClient restClient = RestClient.builder(
                new HttpHost(host, port)
        ).build();

        RestClientTransport transport =
                new RestClientTransport(restClient, new JacksonJsonpMapper());

        this.client = new ElasticsearchClient(transport);

        return this.client;
    }

}