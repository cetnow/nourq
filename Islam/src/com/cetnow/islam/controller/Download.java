package com.cetnow.islam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class Download {

	private static final int BUFFER_SIZE = 4096;
    
	@RequestMapping(method = RequestMethod.GET,value="/download/down/win") 
	public void downloadwin(HttpServletRequest request,
            HttpServletResponse response) throws IOException {

		String fullPath = "/home/media/nour/nour1.exe";
        
		new Download().DownloadFile(fullPath, request, response);


	}
	
	@RequestMapping(method = RequestMethod.GET,value="/download/nourlastversion/win") 
	public void downloadlastwin(HttpServletRequest request,
            HttpServletResponse response) throws IOException {

		String fullPath = "/home/media/nour/nour1.exe";
        
		new Download().DownloadFile(fullPath, request, response);


	}
	
	@RequestMapping(method = RequestMethod.GET,value="/download/down/mac") 
	public void downloadmac(HttpServletRequest request,
            HttpServletResponse response) throws IOException {

		String fullPath = "/home/media/nour/Mac1RC1.zip";
        
		new Download().DownloadFile(fullPath, request, response);


	}
	
	@RequestMapping(method = RequestMethod.GET,value="/download/nourlastversion/mac") 
	public void downloadlastmac(HttpServletRequest request,
            HttpServletResponse response) throws IOException {

		String fullPath = "/home/media/nour/Mac1RC1.zip";
        
		new Download().DownloadFile(fullPath, request, response);


	}
	
	@RequestMapping(method = RequestMethod.GET,value="/download/down/linux") 
	public void downloadlinux(HttpServletRequest request,
            HttpServletResponse response) throws IOException {

		String fullPath = "/home/media/nour/Linux1RC1.zip";
        
		new Download().DownloadFile(fullPath, request, response);


	}
	
	@RequestMapping(method = RequestMethod.GET,value="/download/nourlastversion/linux") 
	public void downloadlastlinux(HttpServletRequest request,
            HttpServletResponse response) throws IOException {

		String fullPath = "/home/media/nour/Linux1RC1.zip";
        
		new Download().DownloadFile(fullPath, request, response);


	}
	
	public void DownloadFile(String fullPath,HttpServletRequest request,
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
        System.out.println("MIME type: " + mimeType);
 
        // set content attributes for the response
        response.setContentType(mimeType);
        response.setContentLength((int) downloadFile.length());
 
        // set headers for the response
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"",
                downloadFile.getName());
        response.setHeader(headerKey, headerValue);
 
        // get output stream of the response
        OutputStream outStream = response.getOutputStream();
 
        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead = -1;
 
        // write bytes read from the input stream into the output stream
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }
 
        inputStream.close();
        outStream.close();
	}
	
}
