package com.cetnow.beans;

import org.hibernate.validator.constraints.*;

import com.cetnow.db.annotations.LookupAnnotation;
import com.cetnow.db.annotations.Unique;

public class MediaInfo extends BaseBean{

	private int SourceFK;
	@NotBlank
	@Unique(column = "MediaUrl", table = "MediaInfos")
	private String MediaUrl;
	@NotBlank
	private String FileName;
	private boolean IsApproved;
	private int Sura;
	private int FromAya;
	private int ToAya;
	private double Start;
	private double End;
	private boolean IsDone;
	
	private int MediaFileFK;
	private int AutherFK;
	private int TypeFK;
	private boolean IsDeleted;
	private int UserFK;
	
	@Override
	public int tableId() {
		return 2;
	}

	public int getSourceFK() {
		return SourceFK;
	}

	public void setSourceFK(int sourceFK) {
		SourceFK = sourceFK;
	}

	public String getMediaUrl() {
		return MediaUrl;
	}

	public void setMediaUrl(String mediaUrl) {
		MediaUrl = mediaUrl;
	}

	public boolean getIsApproved() {
		return IsApproved;
	}

	public void setIsApproved(boolean isApproved) {
		IsApproved = isApproved;
	}

	public int getSura() {
		return Sura;
	}

	public void setSura(int sura) {
		Sura = sura;
	}

	public int getFromAya() {
		return FromAya;
	}

	public void setFromAya(int fromAya) {
		FromAya = fromAya;
	}

	public int getToAya() {
		return ToAya;
	}

	public void setToAya(int toAya) {
		ToAya = toAya;
	}	

	public double getStart() {
		return Start;
	}

	public void setStart(double start) {
		Start = start;
	}

	public double getEnd() {
		return End;
	}

	public void setEnd(double end) {
		End = end;
	}

	public boolean getIsDone() {
		return IsDone;
	}

	public void setIsDone(boolean isDone) {
		IsDone = isDone;
	}

	public int getMediaFileFK() {
		return MediaFileFK;
	}

	public void setMediaFileFK(int mediaFileFK) {
		MediaFileFK = mediaFileFK;
	}

	public String getFileName() {
		return FileName;
	}

	public void setFileName(String fileName) {
		FileName = fileName;
	}

	public int getAutherFK() {
		return AutherFK;
	}

	public void setAutherFK(int autherFK) {
		AutherFK = autherFK;
	}

	public int getTypeFK() {
		return TypeFK;
	}

	public void setTypeFK(int typeFK) {
		TypeFK = typeFK;
	}

	public boolean isIsDeleted() {
		return IsDeleted;
	}

	public void setIsDeleted(boolean isDeleted) {
		IsDeleted = isDeleted;
	}

	public int getUserFK() {
		return UserFK;
	}

	public void setUserFK(int userFK) {
		UserFK = userFK;
	}
	
	
	
	

}
