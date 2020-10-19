package com.poc.springbatch.quartz.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import com.poc.springbatch.quartz.task.TaskOne;
import com.poc.springbatch.quartz.task.TaskTwo;

@Component
public class BatchConfig {

  private Logger LOGGER = LogManager.getLogger(BatchConfig.class);

  @Autowired
  private JobBuilderFactory jobBuilderFactory;
  @Autowired
  private StepBuilderFactory stepBuilderFactory;

  @Bean(name = "jobOneQuartz")
  public Job jobOne() {
    LOGGER.info("jobOneQuartz instantiate");
    return jobBuilderFactory.get("jobOneQuartz").start(step1()).start(step2()).build();
  }

  @Bean(name = "jobTwoQuartz")
  public Job jobTwo() {
    return jobBuilderFactory.get("jobTwoQuartz").flow(step1()).build().build();
  }

  @Bean
  public Step step2() {
    return stepBuilderFactory.get("Step-2Quartz").tasklet(new TaskTwo()).build();
  }

  @Bean
  public Step step1() {
    return stepBuilderFactory.get("Step-1Quartz").tasklet(new TaskOne()).build();
  }

}
