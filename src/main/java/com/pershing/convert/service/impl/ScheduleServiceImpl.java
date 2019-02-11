package com.pershing.convert.service.impl;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.pershing.convert.entities.ConvertData;
import com.pershing.convert.service.ConvertService;
import com.pershing.convert.service.DBService;
import com.pershing.convert.service.ScheduleService;

@Service
public class ScheduleServiceImpl implements ScheduleService {

	@Autowired
	private DBService dbService;
	@Autowired
	private ConvertService convertService;
	@Value("${workingThreads}")
	private int workingThreads;
	
	private Logger logger = LoggerFactory.getLogger(ScheduleServiceImpl.class);
	private boolean isEnable = false;
	private ExecutorService executor;
	
	@Override
	public void runScheduler() {
		dbService.eventRecover();
        isEnable = true;
        executor = Executors.newFixedThreadPool(workingThreads);
	}
	
	@Scheduled(initialDelay = 5000, fixedRate = 30000)
	public void run() {
		if(!isEnable) return;
		List<ConvertData> convertData = dbService.getUnprocessed();
    	for(ConvertData cd:convertData) {
    		logger.info(cd.getcallUuid()+": New job submitted.");
    		
    		executor.submit(new Runnable() {
				@Override
				public void run() {
					convertService.ConvertThread(cd);
				}
			});
    	}
	}
}
