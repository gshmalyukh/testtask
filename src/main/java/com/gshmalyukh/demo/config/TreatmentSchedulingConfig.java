package com.gshmalyukh.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.util.ErrorHandler;

import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableScheduling
@ComponentScan(basePackages = {"com.gshmalyukh.demo.scheduling"})
public class TreatmentSchedulingConfig implements SchedulingConfigurer {
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(updateExecutor());
    }

    @Bean(destroyMethod = "shutdown")
    public Executor updateExecutor() {
        ThreadPoolTaskScheduler updateExecutor =  new ThreadPoolTaskScheduler();
        updateExecutor.setPoolSize(2);
        updateExecutor.setThreadNamePrefix("update-");
        updateExecutor.setErrorHandler(new ErrorHandler() {
            @Override
            public void handleError(Throwable t) {

            }
        });
        updateExecutor.setRejectedExecutionHandler(new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {

            }
        });
        return updateExecutor;
    }
}
