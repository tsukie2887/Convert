package com.pershing.convert.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="convertevent")
@Entity
public class ConvertEvent {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private long ConvertNo;
	@Column
	private String CallUuid;
	@Column
	private String ConvertName;
	@Column
	private String MemberMediaFileName;
	@Column
	private String AgentMediaFileName;
	@Column
	private String Videotime;
	@Column
	private String StartTime;
	@Column
	private String EndTime;
	@Column
	private String ConvertStatus;
	@Column
	private String ErrorMessage;
	/*
	public ConvertEvent() {
	}
	public ConvertEvent(String _calluuid,String _cnvrtnm,String _mmfn,String _amfn) {
		this.CallUuid = _calluuid;
		this.ConvertName = _cnvrtnm;
		this.MemberMediaFileName = _mmfn;
		this.AgentMediaFileName = _amfn;
	}
	*/
	public void setConvertNo(long _evntno) {
		this.ConvertNo = _evntno;
	}
	public long getConvertNo() {
		return this.ConvertNo;
	}
	public void setCallUuid(String _calluuid) {
		this.CallUuid = _calluuid;
	}
	public String getCallUuid() {
		return this.CallUuid;
	}
	public void setConvertName(String _cnvrtnm) {
		this.ConvertName = _cnvrtnm;
	}
	public String getConvertName() {
		return this.ConvertName;
	}
	public void setMemberMediaFileName(String _mmfn) {
		this.MemberMediaFileName = _mmfn;
	}
	public String getMemberMediaFileName() {
		return this.MemberMediaFileName;
	}
	public void setAgentMediaFileName(String _amfn) {
		this.AgentMediaFileName = _amfn;
	}
	public void setVideotime(String _vdtm) {
		this.Videotime = _vdtm;
	}
	public String getVideotime() {
		return this.Videotime;
	}
	public String getAgentMediaFileName() {
		return this.AgentMediaFileName;
	}
	public void setStartTime(String _strttm) {
		this.StartTime = _strttm;
	}
	public String getStartTima() {
		return this.StartTime;
	}
	public void setEndTime(String _endtm) {
		this.EndTime = _endtm;
	}
	public String getEndTime() {
		return this.EndTime;
	}
	public void setConvertStatus(String _cnvrtstt) {
		this.ConvertStatus = _cnvrtstt;
	}
	public String getConvertStatus() {
		return this.ConvertStatus;
	}
	public void setErrorMessage(String _errrmsg) {
		this.ErrorMessage = _errrmsg;
	}
	public String getErrorMessage() {
		return this.ErrorMessage;
	}

}
