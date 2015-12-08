package com.cetnow.util;

public class SiteConfigrations {
	private static boolean hasResources=true;
	private static boolean isProduction=false;
	private static String rootDownlaodFile=isProduction?"/home/media/":"/home/mahmoud/projects/islam/resources/";
	private static String baseURL=isProduction?"http://islam.cetnow.com/":"http://localhost:8080/Islam/";
	private static String domain="http://islam.cetnow.com/";
	private static final int bufferSize = 4096;
	private static final int maxMin=8;
	private static final int minAyat=10;
	private static final boolean loadAllSura=true;
	private static String socialLoginRedirect;
	
	public static boolean isProduction() {
		return isProduction;
	}
	public static String getRootDownlaodFile() {
		return rootDownlaodFile;
	}
	public static String getBaseURL() {
		return baseURL;
	}
	public static boolean isHasResources() {
		return hasResources;
	}
	public static String getDomain() {
		return domain;
	}
	public static int getBufferSize() {
		return bufferSize;
	}
	public static int getMaxmin() {
		return maxMin;
	}
	public static int getMinayat() {
		return minAyat;
	}
	public static boolean isLoadallsura() {
		return loadAllSura;
	}
	
	public static String getSocialLoginRedirect() {
		return isProduction()?"http://islam.cetnow.com/facebooklogin":"http://localhost:8080/Islam/facebooklogin";
	}
	
}
