package com.cetnow.util;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import com.cetnow.beans.FormValidation;

public class Html {
	private @Autowired HttpServletRequest request;
	
	private ReloadableResourceBundleMessageSource mr;
	
	
	public ReloadableResourceBundleMessageSource getMr() {
		return mr;
	}
	public void setMr(ReloadableResourceBundleMessageSource mr) {
		this.mr = mr;
	}
	
	public String ar(String key){
		String result=key;
		try{
			result=mr.getMessage(key, null,new Locale("ar"));
		}catch(Exception ex){}
		return result;
	}
	
	public String ar(String key,String... args){
		String result=key;
		try{
			result=mr.getMessage(key, args,new Locale("ar"));
		}catch(Exception ex){}
		return result;
	}
	
	public boolean isNullOrEmpty(String str){
		return (str == null || str.isEmpty());
	}
	
	public String message(String header,String body,String classes,String icon){
		String result="<div class='"+classes+"'>";
//		result+= isNullOrEmpty(icon)?"":"<i class='"+icon+"'></i>";
		result+=isNullOrEmpty(header)?"":"<div class='header'>"+ar(header)+"</div>";
		result+=isNullOrEmpty(body)?"":"<p>"+body+"</p>";
		result+="</div>";
		return result;
	}
	
	public String positiveMessage(String body){
		return message("messgsucc", body, "ui positive message", "close icon");
	}
	
	public String errorMessage(String header,String body,String icon){
		String result="";
//		result+= isNullOrEmpty(icon)?"":"<i class='"+icon+"'></i>";
		result+=isNullOrEmpty(header)?"":"<div class='header'>"+ar(header)+"</div>";
		result+=isNullOrEmpty(body)?"":"<ul class='list'>"+body+"</ul>";
		return result;
	}
	public boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException nfe) {}
        return false;
    }
	
	public FormValidation validateForm(String successMessage,BindingResult... args){
		FormValidation result=new FormValidation();
		result.setSuccess(true);
		String errors="";
		for(int i=0;i<args.length;i++){
			List<FieldError> oer=args[i].getFieldErrors();
			for(int j=0;j<oer.size();j++){
				result.setSuccess(false);
				FieldError e=oer.get(j);
				errors+="<li>"+ar(e.getField())+" "+ar(e.getDefaultMessage())+"</li>";
			}
		}
		result.setMessage(result.isSuccess()?positiveMessage(successMessage):errorMessage("formsubmiterror",errors,"close icon"));
		return result;
	}
	
	public String objectToJson(Object obj){
		String result="";
		ObjectMapper mapper = new ObjectMapper();
		try{
			result=mapper.writeValueAsString(obj);
		}catch(Exception ex){}
		return result;
	}
	
	public boolean isPost(){
		String res=request.getMethod();
		return !isNullOrEmpty(res) && res.equals("POST");
	}
	
	public String getHref(String link,String title){
		return "<a href='"+link+"'>"+ar(title)+"</a>";
	}
}
