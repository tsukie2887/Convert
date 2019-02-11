package com.pershing.convert.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="ConvertQueue")
@Entity
public class ConvertData {
	@Id
	//	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name="CallUuid")
	private String callUuid;
	@Column(name="MemberMediaFileName")
	private String memberMediaFileName;
	@Column(name="AgentMediaFileName")
	private String agentMediaFileName;
	@Column(name="VideoTime")
	private String videoTime;
	@Column(name="ConvertState")
	private int convertState;
	
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
	public ConvertData() {}
	public ConvertData(String _cu, String amfn, String mmfn, String vdtm, int cnvrtstt) {
		this.setagentMediaFileName(amfn);
		this.setcallUuid(_cu);
		this.setconvertState(cnvrtstt);
		this.setmemberMediaFileName(mmfn);
		this.setvideoTime(vdtm);
	}
	public ConvertData(ConvertData cq) {
		this.setagentMediaFileName(cq.getagentMediaFileName());
		this.setcallUuid(cq.getcallUuid());
		this.setconvertState(cq.getconvertState());
		this.setmemberMediaFileName(cq.getmemberMediaFileName());
		this.setvideoTime(cq.getvideoTime());
	}
}
