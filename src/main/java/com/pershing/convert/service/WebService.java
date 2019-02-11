package com.pershing.convert.service;

import java.lang.String;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pershing.convert.entities.ConvertData;

import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/Api")
public class WebService {

	@Autowired
	private DBService dbService;
	
	private Logger logger = LoggerFactory.getLogger(WebService.class);
	
	@PutMapping("/InsertQueue")
	public String putConvertQueue(@RequestBody ConvertData convertData) throws Exception {
		dbService.eventInsert(convertData);
		logger.info("Data recieved. CallUuid: " + convertData.getcallUuid());
		return "S001";
	}
	
	/*
	//==========================================================================================
	@PutMapping("/InsertEvent")
	public String putEvent(@RequestBody ConvertEvent convertEvent) throws Exception {
		dbService.insertEvent(convertEvent);
		return "S001";
	}
	
	@GetMapping("/GetEvents")
	public String getEvent(@RequestBody ConvertData convertData) throws Exception {
		dbService.insertQueue(convertData);
		return "S001";
	}
	*/
	//for testing
	/*
	@PutMapping("/InsertQueue2")
	public String putConvertQueue2(@RequestBody ConvertData convertData) throws Exception {
		//queueService.addQueue(convertData);
		//convertService.ConvertThread(queueService.takeQueue());
		return "S001";
	}
	*/
}
