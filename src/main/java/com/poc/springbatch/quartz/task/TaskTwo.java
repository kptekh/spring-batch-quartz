package com.poc.springbatch.quartz.task;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class TaskTwo implements Tasklet {
  private static final Logger LOGGER = LogManager.getLogger(Tasklet.class);

  @Override
  public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext)
      throws Exception {
    int max = 10;
    LOGGER.info("TaskTwo started for printing odd numbers between 0 and " + max);
    for (int i = 0; i < max; i++) {
      LOGGER.info(i % 2 == 1 ? i : ++i);
    }
    LOGGER.info("Printed Odd numbers.");
    return RepeatStatus.FINISHED;
  }

}
