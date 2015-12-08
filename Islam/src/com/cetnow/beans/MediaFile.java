package com.cetnow.beans;

public class MediaFile extends BaseBean{

	private String Path;
	private String Extension;
	private String MimeType;
	private double Length;
	private int FileView;
	
	@Override
	public int tableId() {
		return 3;
	}

	public String getPath() {
		return Path;
	}


	public void setPath(String path) {
		Path = path;
	}


	public String getExtension() {
		return Extension;
	}


	public void setExtension(String extension) {
		Extension = extension;
	}


	public String getMimeType() {
		return MimeType;
	}


	public void setMimeType(String mimeType) {
		MimeType = mimeType;
	}


	public double getLength() {
		return Length;
	}


	public void setLength(double length) {
		Length = length;
	}

	public int getFileView() {
		return FileView;
	}

	public void setFileView(int fileView) {
		FileView = fileView;
	}
	
	
	

}
