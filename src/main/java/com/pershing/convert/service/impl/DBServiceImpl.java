package com.pershing.convert.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pershing.convert.entities.ConvertData;
import com.pershing.convert.repositories.ConvertDataRepository;
//import com.feib.mobile.video.datasource.repositories.ConvertEventRepository;
//import com.pershing.convert.HttpService;
import com.pershing.convert.service.DBService;

@Service
public class DBServiceImpl implements DBService {
	@Autowired
	private ConvertDataRepository convertDBR;
	private Logger logger = LoggerFactory.getLogger(DBServiceImpl.class);
	//@Autowired
	//private ConvertEventRepository cer;
	/*
	@Override
	public List<ConvertData> getUnfinishQueue(){
		List<ConvertData> convertData = convertDBR.findAll();
		return convertData;
	}
	*/
	@Override
	public void eventInsert(ConvertData convertData) {
		convertData.setconvertState(0);
		convertDBR.saveAndFlush(convertData);
	}
	@Override
	public void eventStart(ConvertData convertData) {
		convertData.setconvertState(2);
		convertDBR.saveAndFlush(convertData);
	}
	@Override
	public void eventFinish(ConvertData convertData) {
		convertDBR.delete(convertData);;
	}
	@Override
	public void eventError(ConvertData convertData) {
		convertData.setconvertState(1);
		convertDBR.saveAndFlush(convertData);
	}
	@Override
	public void eventRecover() {
		logger.info("Previous Schedules recover...");
		List<ConvertData> convertData = convertDBR.findAll();
		for(ConvertData cd:convertData) {
			eventInsert(cd);
			//cd.setconvertState(0);
			//convertDBR.saveAndFlush(cd);
		}
	}
	@Override
	public List<ConvertData> getUnprocessed(){
		List<ConvertData> convertData = convertDBR.findByConvertState(0);
		for(ConvertData cd: convertData) {
			eventStart(cd);
			//cd.setconvertState(2);
			//convertDBR.saveAndFlush(cd);
		}
		return convertData;
	}
	/*
	@Override
	public void insertEvent(ConvertEvent convertEvent) {
		cer.saveAndFlush(convertEvent);
	}
	*/
}
