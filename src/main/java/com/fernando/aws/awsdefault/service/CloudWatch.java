package com.fernando.aws.awsdefault.service;

import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.cloudwatch.CloudWatchClient;
import software.amazon.awssdk.services.cloudwatch.CloudWatchClientBuilder;
import software.amazon.awssdk.services.cloudwatch.model.*;

@Service
public class CloudWatch {

 public void cloudWatch() {
  CloudWatchClient cw = CloudWatchClient.builder().build();
  boolean done = false;
  String next_token = null;
  while (!done) {

   ListMetricsResponse response;
   String s = "AWS/S3" ;
   if (next_token == null) {
    ListMetricsRequest request = ListMetricsRequest.builder()
            .namespace(s)
            .build();
    response = cw.listMetrics(request);
   } else {
    ListMetricsRequest request = ListMetricsRequest.builder()
            .namespace(s)
            .nextToken(next_token)
            .build();
    response = cw.listMetrics(request);
   }
   for (Metric metric : response.metrics()) {
    System.out.printf(
            "Retrieved metric %s", metric.metricName());
    System.out.println();
   }
   if (response.nextToken() == null) {
    done = true;
   } else {
    next_token = response.nextToken();
   }
  }
 }

 public void putMetric(String val){
  if(val != null) {
   Double data_point = Double.parseDouble(val);
   CloudWatchClient cw =
           CloudWatchClient.builder().build();
   Dimension dimension = Dimension.builder()
           .name("UNIQUE_PAGES")
           .value("URLS").build();
   MetricDatum datum = MetricDatum.builder()
           .metricName("PAGES_VISITED")
           .unit(StandardUnit.NONE)
           .value(data_point)
           .dimensions(dimension).build();
   PutMetricDataRequest request = PutMetricDataRequest.builder()
           .namespace("SITE/TRAFFIC")
           .metricData(datum).build();
   PutMetricDataResponse response = cw.putMetricData(request);

   System.out.println(response);
  }

 }

 public void meusAlarmes() {
  CloudWatchClient cw = CloudWatchClient.builder().build();
  boolean done = false;
  String new_token = null;
  while (!done) {
   DescribeAlarmsResponse response;
   if (new_token == null) {
    DescribeAlarmsRequest request = DescribeAlarmsRequest.builder().build();
    response = cw.describeAlarms(request);
   } else {
    DescribeAlarmsRequest request = DescribeAlarmsRequest.builder()
            .nextToken(new_token)
            .build();
    response = cw.describeAlarms(request);
   }
   for (MetricAlarm alarm : response.metricAlarms()) {
    System.out.printf("Retrieved alarm %s", alarm.alarmName());
   }
   if (response.nextToken() == null) {
    done = true;
   } else {
    new_token = response.nextToken();
   }
  }
 }
}
