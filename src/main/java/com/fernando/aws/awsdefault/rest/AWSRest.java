package com.fernando.aws.awsdefault.rest;

import com.fernando.aws.awsdefault.service.CloudWatch;
import com.fernando.aws.awsdefault.service.DynamoDB;
import com.fernando.aws.awsdefault.service.EC2;
import com.fernando.aws.awsdefault.service.S3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/rest/aws")
public class AWSRest {

    private final S3 s3;
    private final EC2 ec2;
    private final DynamoDB dynamoDB;
    private final CloudWatch cloudWatch;

    @Autowired
    public AWSRest(S3 s3, EC2 ec2, DynamoDB dynamoDB, CloudWatch cloudWatch) {
        this.s3 = s3;
        this.ec2 = ec2;
        this.dynamoDB = dynamoDB;
        this.cloudWatch = cloudWatch;
    }

    @GetMapping
    public String aws(@RequestParam("val") String val){

//        s3.s3();
//        s3.s3Async();
//        s3.s3AsyncOut();
//        ec2.ec2();
//        dynamoDB.dynamoDbAsync();
        cloudWatch.cloudWatch();
        cloudWatch.putMetric(val);
        cloudWatch.meusAlarmes();

        return "HelloWorld";

    }

}
