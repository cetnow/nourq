package com.cetnow.beans;

import java.util.Date;

public class MediaApproval extends BaseBean{

	@Override
	public int tableId() {
		return 7;
	}	
	
	private int ApprovedByFK;
	private Date ApprovalDate;
	private int MediaInfoFK;
	private String Comments;
	
	public int getApprovedByFK() {
		return ApprovedByFK;
	}
	public void setApprovedByFK(int approvedByFK) {
		ApprovedByFK = approvedByFK;
	}
	public Date getApprovalDate() {
		return ApprovalDate;
	}
	public void setApprovalDate(Date approvalDate) {
		ApprovalDate = approvalDate;
	}
	public int getMediaInfoFK() {
		return MediaInfoFK;
	}
	public void setMediaInfoFK(int mediaInfoFK) {
		MediaInfoFK = mediaInfoFK;
	}
	public String getComments() {
		return Comments;
	}
	public void setComments(String comments) {
		Comments = comments;
	}
	
	

}
