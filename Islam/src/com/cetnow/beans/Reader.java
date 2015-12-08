package com.cetnow.beans;

public class Reader {

	private int Id;
	private String Name;
	private String Path;
	private String Quality;
	private boolean Arabic;
	private boolean IsActive;
	
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getPath() {
		return Path;
	}
	public void setPath(String path) {
		Path = path;
	}
	public String getQuality() {
		return Quality;
	}
	public void setQuality(String quality) {
		Quality = quality;
	}
	public boolean isArabic() {
		return Arabic;
	}
	public void setArabic(boolean arabic) {
		Arabic = arabic;
	}
	public boolean isIsActive() {
		return IsActive;
	}
	public void setIsActive(boolean isActive) {
		IsActive = isActive;
	}
	
	
}
