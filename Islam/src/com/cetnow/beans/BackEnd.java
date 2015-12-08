package com.cetnow.beans;

import java.util.Date;

public class BackEnd extends BaseBean{

	private String ServiceName;
	private Date TimeStampTry;
	private String ServiceResult;
	private boolean IsDone;
	
	@Override
	public int tableId() {
		// TODO Auto-generated method stub
		return 8;
	}

	public String getServiceName() {
		return ServiceName;
	}

	public void setServiceName(String serviceName) {
		ServiceName = serviceName;
	}

	public Date getTimeStampTry() {
		return TimeStampTry;
	}

	public void setTimeStampTry(Date timeStampTry) {
		TimeStampTry = timeStampTry;
	}

	public String getServiceResult() {
		return ServiceResult;
	}

	public void setServiceResult(String serviceResult) {
		ServiceResult = serviceResult;
	}

	public boolean isIsDone() {
		return IsDone;
	}

	public void setIsDone(boolean isDone) {
		IsDone = isDone;
	}
	
	

}
