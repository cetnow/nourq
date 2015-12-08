package com.cetnow.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component()
public class Shared implements Serializable{

	private static final long serialVersionUID = 8833925738672042695L;
	
	private Map<String, String> langs = new HashMap<String, String>();

	public Map<String, String> getLangs() {
		return langs;
	}

	public void setLangs(Map<String, String> langs) {
		this.langs = langs;
	}
	
	public String getBaseUrl(){
		return SiteConfigrations.getBaseURL();
	}
	

}
