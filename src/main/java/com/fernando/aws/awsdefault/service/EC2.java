package com.fernando.aws.awsdefault.service;

import org.springframework.stereotype.Service;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ec2.Ec2Client;

@Service
public class EC2 {

    public void ec2(){
        Ec2Client ec2 = Ec2Client.builder()
                .region(Region.US_WEST_2)
                .build();

        System.out.println(ec2);
    }
}
