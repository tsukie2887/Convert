package com.pershing.convert.model;

import org.springframework.stereotype.Component;

@Component
public class convertQueueModel {
	private String callUuid;
	private String memberMediaFileName;
	private String agentMediaFileName;
	private String videoTime;
	private int convertState;
	
	public convertQueueModel() {
		
	}
	
	public String getcallUuid() {
		return callUuid;
	}
	public String getagentMediaFileName() {
		return agentMediaFileName;
	}
	public String getmemberMediaFileName() {
		return memberMediaFileName;
	}
	public String getvideoTime() {
		return videoTime;
	}
	public int getconvertState() {
		return convertState;
	}
	public void setcallUuid(String calluuid) {
		this.callUuid = calluuid;
	}
	public void setagentMediaFileName(String amfn) {
		this.agentMediaFileName = amfn;
	}
	public void setmemberMediaFileName(String mmfn) {
		this.memberMediaFileName = mmfn;
	}
	public void setvideoTime(String vdtm) {
		this.videoTime = vdtm;
	}
	public void setconvertState(int cnvrtstt) {
		this.convertState = cnvrtstt;
	}
}
