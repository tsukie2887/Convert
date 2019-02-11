package com.pershing.convert;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.pershing.convert.service.ScheduleService;
//import com.pershing.convert.service.impl.ScheduleServiceImpl;

@SpringBootApplication
@EnableConfigurationProperties
@ComponentScan({ "com.pershing.convert.entities", "com.pershing.convert" })
@EntityScan("com.pershing.convert.*")
@EnableJpaRepositories("com.pershing.convert.repositories")
@EnableScheduling
public class ConvertApp implements CommandLineRunner {
	
	@Autowired
	private ScheduleService scheduleService;
	
	private Logger logger = LoggerFactory.getLogger(ConvertApp.class);

	public static void main(String[] args) {
		SpringApplication.run(ConvertApp.class, args);
	}
	
	@Override		
    public void run(String... args) {
		logger.info("Scheduler start...");
		scheduleService.runScheduler();
    }
}
