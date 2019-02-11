package com.pershing.convert.service;

import java.util.List;

import com.pershing.convert.entities.ConvertData;

public interface DBService {
	//List<ConvertData> getUnfinishQueue();
	void eventInsert(ConvertData convertData);
	void eventStart(ConvertData convertData);
	void eventFinish(ConvertData convertData);
	void eventError(ConvertData convertData);
	
	void eventRecover();
	List<ConvertData> getUnprocessed();
	
	//void insertEvent(ConvertEvent convertEvent);
	
}