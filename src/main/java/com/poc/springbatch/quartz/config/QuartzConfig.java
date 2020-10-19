package com.poc.springbatch.quartz.config;

import java.io.IOException;
import java.util.Properties;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import com.poc.springbatch.quartz.job.CustomQuartzJob;

@Configuration
public class QuartzConfig {

  @Autowired
  private JobLauncher jobLauncher;
  @Autowired
  private JobLocator jobLocator;

  @Bean
  public JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor(JobRegistry jobRegistry) {
    JobRegistryBeanPostProcessor processor = new JobRegistryBeanPostProcessor();

    processor.setJobRegistry(jobRegistry);
    return processor;
  }

  @Bean()
  public JobDetail jobTwoDetails() {
    JobDataMap newJobDataMap = new JobDataMap();
    newJobDataMap.put("jobName", "jobTwoQuartz");
    newJobDataMap.put("jobLauncher", jobLauncher);
    newJobDataMap.put("jobLocator", jobLocator);
    return JobBuilder.newJob(CustomQuartzJob.class).withIdentity("jobTwoQuartz")
        .setJobData(newJobDataMap).storeDurably().build();
  }

  @Bean()
  public JobDetail jobOneDetails() {
    JobDataMap newJobDataMap = new JobDataMap();
    newJobDataMap.put("jobName", "jobOneQuartz");
    newJobDataMap.put("jobLauncher", jobLauncher);
    newJobDataMap.put("jobLocator", jobLocator);

    return JobBuilder.newJob(CustomQuartzJob.class).withIdentity("jobOneQuartz")
        .setJobData(newJobDataMap).storeDurably().build();
  }

  @Bean
  public Trigger jobTwoTrigger() {
    SimpleScheduleBuilder builder =
        SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(20).repeatForever();
    return TriggerBuilder.newTrigger().forJob(jobTwoDetails()).withIdentity("jobTwoTrigger")
        .withSchedule(builder).build();
  }

  @Bean
  public Trigger jobOneTrigger() {
    SimpleScheduleBuilder builder =
        SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(10).repeatForever();
    return TriggerBuilder.newTrigger().forJob(jobOneDetails()).withIdentity("jobOneTrigger")
        .withSchedule(builder).build();
  }

  @Bean
  public SchedulerFactoryBean schedularFactoryBean() throws IOException {
    SchedulerFactoryBean bean = new SchedulerFactoryBean();
    bean.setTriggers(jobOneTrigger(), jobTwoTrigger());
    bean.setQuartzProperties(getQuartzProperties());
    bean.setJobDetails(jobOneDetails(), jobTwoDetails());
    return bean;
  }


  @Bean
  public Properties getQuartzProperties() throws IOException {
    PropertiesFactoryBean bean = new PropertiesFactoryBean();
    bean.setLocation(new ClassPathResource("quartz.properties"));
    bean.afterPropertiesSet();

    return bean.getObject();
  }


}
