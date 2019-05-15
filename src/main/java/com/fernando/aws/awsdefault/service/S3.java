package com.fernando.aws.awsdefault.service;

import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.core.async.AsyncResponseTransformer;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;

@Service
public class S3 {

    private static final String BUCKET = "static-jogadores.fernando.com.br";
    private static final String KEY = "ajuda-md";

    public void s3() {

//        S3Client.serviceMetadata().regions().forEach(System.out::println);
        S3Client s3 = S3Client.builder()
//                .credentialsProvider(EnvironmentVariableCredentialsProvider.create())
                .build();

        s3.close();

        Ec2Client ec2 = Ec2Client.builder()
                .region(Region.US_WEST_2)
                .build();

    }

    public void s3Async() {

        S3AsyncClient client = S3AsyncClient.create();
        CompletableFuture<PutObjectResponse> future = client.putObject(
                PutObjectRequest.builder()
                        .bucket(BUCKET)
                        .key(KEY)
                        .build(),
                AsyncRequestBody.fromFile(Paths.get("HELP.md"))
        );
        future.whenComplete((resp, err) -> {
            try {
                if (resp != null) {
                    System.out.println("my response: " + resp);
                } else {
                    // Handle error
                    err.printStackTrace();
                }
            } finally {
                client.close();
            }
        });
        future.join();
    }


    public void s3AsyncOut() {

        S3AsyncClient client = S3AsyncClient.create();
        Path path = Paths.get("myfile.out");
        path.toFile().delete();
        final CompletableFuture<GetObjectResponse> futureGet = client.getObject(
                GetObjectRequest.builder()
                        .bucket(BUCKET)
                        .key(KEY)
                        .build(),
                AsyncResponseTransformer.toFile(path)
        );

        futureGet.whenComplete((resp, err) -> {
            try {
                if (resp != null) {
                    System.out.println("my response: " + resp);
                } else {
                    // Handle error
                    err.printStackTrace();
                }
            } finally {
                client.close();
            }
        });
        futureGet.join();
    }
}

