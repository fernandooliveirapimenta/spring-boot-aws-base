package com.fernando.aws.awsdefault.service;

import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.dynamodb.model.ListTablesRequest;
import software.amazon.awssdk.services.dynamodb.model.ListTablesResponse;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class DynamoDB {

    public void dynamoDbAsync() {
        DynamoDbAsyncClient client = DynamoDbAsyncClient.create();
        CompletableFuture<ListTablesResponse> response =
                client.listTables(ListTablesRequest.builder().build());
        // Map the response to another CompletableFuture containing just the table names
        CompletableFuture<List<String>> tableNames =
                response.thenApply(ListTablesResponse::tableNames);
        tableNames.whenComplete((tables, err) -> {
            try {
                if (tables != null) {
                    tables.forEach(System.out::println);
                } else {
                    err.printStackTrace();
                }
            } finally {
                client.close();
            }
        });

        tableNames.join();

    }
}
