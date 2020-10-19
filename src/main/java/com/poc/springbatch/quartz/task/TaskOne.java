package com.poc.springbatch.quartz.task;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class TaskOne implements Tasklet {

  private static final Logger LOGGER = LogManager.getLogger(TaskOne.class);

  @Override
  public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext)
      throws Exception {
    int max = 10;
    LOGGER.info("TaskOne started for printing even numbers between 0 and " + max);
    for (int i = 0; i < max; i++) {
      LOGGER.info(i % 2 == 0 ? i : ++i);
    }
    LOGGER.info("Printed Even numbers");
    return RepeatStatus.FINISHED;
  }

}
