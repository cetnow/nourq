package com.cetnow.util;

import java.awt.Point;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.SecureDirectoryStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.*;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import com.cetnow.beans.Sura;
import com.cetnow.db.DAL;

public class FilesUtil {
	@Autowired
	private DAL dal; 
	
	public void viewFile(String fullPath,HttpServletRequest request,
            HttpServletResponse response) throws IOException{
		
		File file = new File(fullPath);

         
        if(!file.exists()){
            String errorMessage = "Sorry. The file you are looking for does not exist";
            System.out.println(errorMessage);
            OutputStream outputStream = response.getOutputStream();
            outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
            outputStream.close();
            return;
        }
         
        String mimeType= "audio/mpeg";

         
         
        //response.setContentType(mimeType);
         
        /* "Content-Disposition : inline" will show viewable types [like images/text/pdf/anything viewable by browser] right on browser 
            while others(zip e.g) will be directly downloaded [may provide save as popup, based on your browser setting.]*/
        response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() +"\""));
 
         
        /* "Content-Disposition : attachment" will be directly download, may provide save as popup, based on your browser setting*/
        //response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
         
        response.setContentLength((int)file.length());
        response.setHeader("Content-Type", mimeType);
        response.setHeader("Accept-Ranges", "bytes");
        response.setHeader("Access-Control-Allow-Headers", "range, accept-encoding");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Content-Range", "bytes */" + file.length());
 
        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
 
        //Copy bytes from source to destination(outputstream in this example), closes both streams.
        FileCopyUtils.copy(inputStream, response.getOutputStream());
	
	}
	public void downloadFile(String fullPath,HttpServletRequest request,
            HttpServletResponse response) throws IOException{
		// get absolute path of the application
 
        // construct the complete absolute path of the file     
        File downloadFile = new File(fullPath);
        FileInputStream inputStream = new FileInputStream(downloadFile);
         
        // get MIME type of the file
        String mimeType = Files.probeContentType(Paths.get(fullPath));
        if (mimeType == null) {
            // set to binary type if MIME mapping not found
            mimeType = "application/octet-stream";
        }
 
        // set content attributes for the response
        response.setContentType(mimeType);
        response.setContentLength((int) downloadFile.length());
 
        // set headers for the response
        String headerKey = "Content-Disposition";
        String headerValue = String.format("inline; filename=\"%s\"",
                downloadFile.getName());
        response.setHeader(headerKey, headerValue);
        response.setHeader("Accept-Ranges", "bytes");
        response.setHeader("Access-Control-Allow-Headers", "range, accept-encoding");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Content-Range", "bytes */" + downloadFile.length());
 
        // get output stream of the response
        OutputStream outStream = response.getOutputStream();
 
        byte[] buffer = new byte[SiteConfigrations.getBufferSize()];
        int bytesRead = -1;
 
        // write bytes read from the input stream into the output stream
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }
 
        inputStream.close();
        outStream.close();
	}
	
	
	public void downloadListFile(List<String> files,HttpServletRequest request,
            HttpServletResponse response) throws IOException{
        
		String mimeType = Files.probeContentType(Paths.get(files.get(0)));
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }
 
        response.setContentType(mimeType);
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"",
        		"11.mp3");
        response.setHeader(headerKey, headerValue);
 
        OutputStream outStream = response.getOutputStream();
 
        byte[] buffer = new byte[SiteConfigrations.getBufferSize()];
        int bytesRead = -1;
 
        int fileslengths=0;
        
        for(String fullPath : files){
        	
        File downloadFile = new File(fullPath);
        fileslengths +=downloadFile.length();
        FileInputStream inputStream = new FileInputStream(downloadFile);
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }
        
        inputStream.close();
        }
        response.setContentLength(fileslengths);
        outStream.close();
	}
	
	
	public void getAyatMp3(String filename,List<String> files,long fileLength,HttpServletRequest request,HttpServletResponse response) throws IOException{
 
        String mimeType="audio/mpeg";
        response.setContentType(mimeType);
 
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"",filename);
        response.setHeader(headerKey, headerValue);
        
        OutputStream outStream = response.getOutputStream();
 
        byte[] buffer = new byte[SiteConfigrations.getBufferSize()];
        int bytesRead = -1;
 
        response.setContentLength((int)fileLength);
        for(String fullPath : files){
        	String tempPath=SiteConfigrations.isHasResources()?SiteConfigrations.getRootDownlaodFile()+fullPath:SiteConfigrations.getDomain()+fullPath.replaceAll("download", "quranverses");
        	File downloadFile = new File(tempPath);
	        InputStream inputStream = SiteConfigrations.isHasResources()?new FileInputStream(downloadFile):new URL(tempPath).openStream();
	        while ((bytesRead = inputStream.read(buffer)) != -1) {
	            outStream.write(buffer, 0, bytesRead);
	        }
	        
	        inputStream.close();
        }
        
        outStream.close();
	}

	public  static Map<String,Object> getAyatStreamResult(DAL dal,UserConfiguration uc,int Sura,int FromAya){
		ResultSet rs=null;
		Map<String,Object> result=new HashMap<String,Object>();
		try {
			Sura sura=dal.getSura(Sura);
			int maxAya=getMaxAyat(dal,uc, Sura, FromAya);
			result.put("mp3URL", SiteConfigrations.getBaseURL()+"streamayat?sura="+Sura+"&fromAya="+FromAya+"&ayaTo="+maxAya+"&reader="+uc.getReaderId());
			
			
			result.put("remaining",(sura.getAyaCount()-maxAya));
			
			result.put("from",FromAya);
			
			result.put("current",0);
			
			result.put("to",maxAya);
			
			result.put("chunkcount",(maxAya-FromAya));
			
			result.put("ayatcount",sura.getAyaCount());
			
			result.put("sura",Sura);
			
			result.put("ayatdata", SiteConfigrations.getBaseURL()+"loadayat?sura="+Sura+"&fromAya="+FromAya+"&toAya="+maxAya);
			result.put("bookdata", SiteConfigrations.getBaseURL()+"loadbooks?sura="+Sura+"&fromAya="+FromAya+"&toAya="+maxAya+"&bookId="+uc.getBookId());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static int getMaxAyat(DAL dal,UserConfiguration uc,int Sura,int FromAya){
		int result=SiteConfigrations.getMinayat()+FromAya;
		int Ayatnumber=0;
		try{
		Sura sura=dal.getSura(Sura);
		Map<String,Object> mp3sec=dal.getMp3Secs(uc, Sura, FromAya);
		Ayatnumber=sura.getAyaCount();
		if(SiteConfigrations.isLoadallsura()){
			if(Integer.parseInt(mp3sec.get("secs")+"")<=SiteConfigrations.getMaxmin()){
				result=Integer.parseInt(mp3sec.get("lastaya")+"");
				return result;
			}
		}
		while(!isMaxAya(dal,uc,Sura,FromAya,result,Ayatnumber)){
			result +=SiteConfigrations.getMinayat();
		}
		System.out.println("result="+result);
		result=(result>Ayatnumber)?Ayatnumber:result;
		
		}catch(Exception ex){}
		
		return result;
	}
	
	public static boolean isMaxAya(DAL dal,UserConfiguration uc,int Sura,int FromAya,int toAya,int max){
		boolean result=false;
		Map<String,Object> mp3sec=dal.getMp3Secs(uc, Sura, FromAya);

		if(Integer.parseInt(mp3sec.get("secs")+"")>=SiteConfigrations.getMaxmin() || toAya>=max)
			result=true;
			
		return result;
	}
	
	public void downloadMP3(byte[] data,HttpServletRequest request,HttpServletResponse response) throws IOException{
		String name="mm.mp3";
        // set content attributes for the response
        response.setContentType("audio/mpeg3");
        response.setContentLength(data.length);
 
        // set headers for the response
        String headerKey = "Content-Disposition";
        String headerValue = String.format("inline; filename=\"%s\"",name);
        response.setHeader(headerKey, headerValue);
        response.setHeader("Accept-Ranges", "bytes");
        response.setHeader("Access-Control-Allow-Headers", "range, accept-encoding");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Content-Range", "bytes */" + data.length);
 
        // get output stream of the response
        OutputStream outStream = response.getOutputStream();
        outStream.write(data, 0, data.length);
 
        outStream.close();
	}
	
	public void downloadMP3(int reader,int sura , int from , int to,HttpServletRequest request,HttpServletResponse response) throws IOException{
		Point point= dal.getCalcMP3(reader, sura, from, to);
		String fullPath = "/home/mahmoud/Downloads/001_02.mp3";
		new FilesUtil().downloadMP3(new MP3Util(fullPath).getDataByBitRate(point.x, point.y), request, response);
	}
	
}
