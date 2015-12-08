package com.cetnow.beans;

import org.hibernate.validator.constraints.NotBlank;

public class MediaAuthor {
	@NotBlank
	private String MediaAutherName;
	@NotBlank
	private String MediaType;

	public String getMediaAutherName() {
		return MediaAutherName;
	}

	public void setMediaAutherName(String mediaAutherName) {
		MediaAutherName = mediaAutherName;
	}

	public String getMediaType() {
		return MediaType;
	}

	public void setMediaType(String mediaType) {
		MediaType = mediaType;
	}
	
	
	
}
