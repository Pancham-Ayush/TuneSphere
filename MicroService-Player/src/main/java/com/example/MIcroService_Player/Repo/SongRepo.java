package com.example.MIcroService_Player.Repo;

import com.example.MIcroService_Player.Model.SongMeta;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.GetItemResponse;

import java.util.HashMap;
import java.util.Map;

@Repository
public class SongRepo {

    private final DynamoDbClient dynamoDbClient;

    public SongRepo(DynamoDbClient dynamoDbClient) {
        this.dynamoDbClient = dynamoDbClient;
    }

    public SongMeta findMetaById(String id) {

        Map<String, AttributeValue> key = new HashMap<>();
        key.put("id", AttributeValue.builder().s(id).build());

        GetItemRequest request = GetItemRequest.builder()
                .tableName("Song")
                .key(key)
                .projectionExpression("#p, #s")
                .expressionAttributeNames(Map.of(
                        "#p", "path",
                        "#s", "size"
                ))
                .build();

        GetItemResponse response = dynamoDbClient.getItem(request);

        Map<String, AttributeValue> item = response.item();

        if (item == null || item.isEmpty()) {
            throw new RuntimeException("Song not found");
        }

        String path = item.get("path").s();
        long size = Long.parseLong(item.get("size").n());

        return new SongMeta(id, path, size);
    }
}