package com.poc.springbatch.quartz;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class QuartzApplication {

  public static void main(String[] args) {
    SpringApplication.run(QuartzApplication.class, args);
  }

}
