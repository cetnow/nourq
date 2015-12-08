package com.cetnow.islam.controller;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.json.JsonArray;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import javax.validation.Valid;

import org.apache.commons.beanutils.BeanUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cetnow.beans.Audit;
import com.cetnow.beans.Book;
import com.cetnow.beans.FormValidation;
import com.cetnow.beans.Lookup;
import com.cetnow.beans.MediaApproval;
import com.cetnow.beans.MediaAuthor;
import com.cetnow.beans.MediaFile;
import com.cetnow.beans.MediaInfo;
import com.cetnow.beans.MediaSource;
import com.cetnow.beans.QuranSearch;
import com.cetnow.beans.Sura;
import com.cetnow.db.BeanUtil;
import com.cetnow.db.DAL;
import com.cetnow.util.Convertor;
import com.cetnow.util.FilesUtil;
import com.cetnow.util.Html;
import com.cetnow.util.Session;
import com.cetnow.util.Shared;
import com.cetnow.util.UserConfiguration;
import com.cetnow.util.Util;
import com.mysql.jdbc.Connection;


@Controller
public class Home  {

	
	@Autowired
	private UserConfiguration conf;
	
	@Autowired
	private DAL dal; 
	
	@Autowired
	private Html html;
	
	@Autowired
	private Session session;
	
	@Autowired
	private FilesUtil filesUtil;
	
	@RequestMapping(value = "/404")
	public String handle404(ModelMap model) {
	    return "errors/404";
	}
	
    
	@RequestMapping(value = "/Set")
	public void set(ModelMap model,HttpServletRequest request,HttpServletResponse response) throws IOException {
		filesUtil.downloadMP3(25, 1, 1, 3,request,response);
		//session.setAttribute("success1" , "successfully accessed");	
	    //return "set";
	}
	
	@RequestMapping(value = "/")
	public String home(ModelMap model,HttpSession session) {
		model.addAttribute("b1" , dal.getBooksByCat("tafseer"));
		model.addAttribute("b2" , dal.getBooksByCat("trans"));
		model.addAttribute("readers",dal.getActiveReaders());
	    return "components/viewaya";
	}
	@RequestMapping(value = "/viewaya")
	public String viewaya(ModelMap model,HttpSession session) {
		model.addAttribute("b1" , dal.getBooksByCat("tafseer"));
		model.addAttribute("b2" , dal.getBooksByCat("trans"));
		model.addAttribute("readers",dal.getActiveReaders());
	    return "components/viewaya";
	}
	
	@RequestMapping(value = "/searchaya")
	public String searchaya(ModelMap model,HttpSession session,String query) {
		//System.out.println(dal.getAyaSearchresult(query));
		model.addAttribute("l" ,dal.getAyaSearchresult(query.trim()));
		
	    return "components/table";
	}
	
	@RequestMapping(value = "/loadayat", produces = {"application/json; charset=UTF-8"})
	@ResponseBody
	public String loadayat(int sura,int fromAya,int toAya) {
	    return dal.getLoadAyatResults(conf, sura, fromAya, toAya).toString();
	}
	
	@RequestMapping(value = "/loadbooks", produces = {"application/json; charset=UTF-8"})
	@ResponseBody
	public String loadbooks(int sura,int fromAya,int toAya,int bookId,int selectedBook) {
		conf.setBookId(selectedBook);
	    return dal.getBookContentsResult(conf, sura, fromAya, toAya).toString();
	}
	
	@RequestMapping(value = "/streamayat")
	public void  streamayat(int sura,int fromAya,int ayaTo,int reader,HttpServletRequest request,HttpServletResponse response) throws IOException  {
		conf.setReaderId(reader);
	    new FilesUtil().getAyatMp3(dal.getFileName(conf, sura, fromAya, ayaTo),dal.getFilesBySura(conf, sura, fromAya, ayaTo),dal.getQuranMp3Length(conf.getReaderId(),sura,fromAya,ayaTo), request, response);
	}
	
	@RequestMapping(value = "/getsreaminfo", produces = {"application/json; charset=UTF-8"})
	@ResponseBody
	public Object getsreaminfo(int sura,int fromAya,int selectedBook) {
		conf.setBookId(selectedBook);
	    return FilesUtil.getAyatStreamResult(dal,conf, sura, fromAya);
	}
	
	@RequestMapping(value = "/updateuserconfig", method = RequestMethod.POST)
	@ResponseBody
	public String updateuserconfig(@Valid UserConfiguration uc, BindingResult result) {
		return "Done";
	}
	
	@RequestMapping(value = "/updatereader", method = RequestMethod.POST)
	@ResponseBody
	public String updatereader(int readerId) {
		conf.setReaderId(readerId);
		return "Done";
 
	}
	
	@RequestMapping(value="/form")
    public String greetingSubmitt(@Valid Book book, BindingResult bindingResult, ModelMap model) {
		System.out.println("ww");
        model.addAttribute("book", book);
        model.addAttribute("e", bindingResult.getAllErrors());
        return "home/form";
    }
	
	@RequestMapping(value = "/getcurrentreader", method = RequestMethod.POST)
	@ResponseBody
	public String getcurrentreader() {
		return conf.getReaderId()+"";
 
	}
	
	@RequestMapping(value = "/getallsura", produces = {"application/json; charset=UTF-8"})
	@ResponseBody
	public String getAllSura() {
	    return dal.getAllSura().toString();
	}
	
	@RequestMapping(value = "/{catFK}/lookupsearch", produces = {"application/json; charset=UTF-8"})
	@ResponseBody
	public Object lookupsearch(ModelMap model,@PathVariable(value = "catFK") int catFK,String q) {
		JSONObject result=new JSONObject();

		JSONArray res=dal.getLookup(catFK, q);
		
		Map<String,Object> def=new HashMap<String,Object>();
		def.put("name", q);
		def.put("value", q);
		res.put(def);
		result.put("success", true);
		result.put("results", res);
		
	    return result.toString();
	}
	
	@RequestMapping(value = "/ayamediainfo")
	public String ayamediainfo(ModelMap model,int sura,int aya) {
		Sura s=dal.getById(sura, Sura.class);
		model.addAttribute("data", dal.getMediaInfoByAya(sura, aya));
		model.addAttribute("header",html.ar("yamediaheader",s.getArName(),""+aya));
		return "components/ayamediainfo";
	}
	
	@RequestMapping(value="/getfile") 
	public void downloadlastmac(int id,HttpServletRequest request,HttpServletResponse response) throws IOException {
		//String fullPath = "/home/mahmoud/Downloads/c28yf2kfanxzfnlihj5d.mp3";
		MediaFile mf=dal.downloadFile(id);
		new FilesUtil().downloadFile(mf.getPath(), request, response);
	}
	
	@RequestMapping(value = "/mediaapproval")
	public String mediaapproval(ModelMap model,HttpServletRequest request) {
		if(!session.isAdmin())
			return "redirect:/" ;
		if(html.isPost()){
			
			Lookup tl=null;
			Lookup al=null;
			MediaInfo mi=null;
			
			if(!html.isNullOrEmpty(request.getParameter("sub"))){
				
				MediaApproval ma =new MediaApproval();
				
				ma.setApprovedByFK(session.getUser().getId());
				ma.setApprovalDate(new Date());
				
				String Id=request.getParameter("sub");
				mi=dal.getById(Id, MediaInfo.class);
				String FileName=request.getParameter("FileName"+Id);
				mi.setIsApproved(true);
				mi.setFileName(FileName);
				
				ma.setMediaInfoFK(mi.getId());
				
				String type=request.getParameter("TypeValue"+Id);
				String auth=request.getParameter("AutherValue"+Id);
				
				tl=dal.getById(mi.getTypeFK(), Lookup.class);
				al=dal.getById(mi.getAutherFK(), Lookup.class);
				
				if(!html.isInteger(type))
					tl.setName(type);
				else
					tl=dal.getById(type, Lookup.class);
				
				if(!html.isInteger(auth))
					al.setName(auth);
				else
					al=dal.getById(auth, Lookup.class);
				
				tl.setIsApproved(true);
				al.setIsApproved(true);
				
				dal.saveOrUpdate(tl);
				dal.saveOrUpdate(al);
				dal.saveOrUpdate(mi);
				dal.saveOrUpdate(ma);
			}
			
			if(!html.isNullOrEmpty(request.getParameter("delm"))){
				mi=dal.getById(request.getParameter("delm"), MediaInfo.class);
				mi.setIsDeleted(true);
				dal.saveOrUpdate(mi);
			}
			
			if(!html.isNullOrEmpty(request.getParameter("delt"))){
				mi=dal.getById(request.getParameter("delt"), MediaInfo.class);
				tl=dal.getById(mi.getTypeFK(), Lookup.class);
				tl.setIsDeleted(true);
				dal.saveOrUpdate(tl);
			}
			
			if(!html.isNullOrEmpty(request.getParameter("dela"))){
				mi=dal.getById(request.getParameter("dela"), MediaInfo.class);
				al=dal.getById(mi.getAutherFK(), Lookup.class);
				al.setIsDeleted(true);
				dal.saveOrUpdate(al);
			}
			
			if(!html.isNullOrEmpty(request.getParameter("delall"))){
				mi=dal.getById(request.getParameter("delall"), MediaInfo.class);
				tl=dal.getById(mi.getTypeFK(), Lookup.class);
				al=dal.getById(mi.getAutherFK(), Lookup.class);
				mi.setIsDeleted(true);
				dal.saveOrUpdate(mi);
				tl.setIsDeleted(true);
				dal.saveOrUpdate(tl);
				al.setIsDeleted(true);
				dal.saveOrUpdate(al);
			}
		}
		
		model.addAttribute("data" ,dal.getMediaInfos(false));
		return "components/mediaapproval";
	}
	
	@RequestMapping(value = "/getMediaTypes")
	public String getMediaTypes(ModelMap model) {
		model.addAttribute("types",dal.queryToMapList("select * from Lookups where CatFK=2 and IsDeleted=0"));
		return "components/mediatype";
	}
	
	@RequestMapping(value = "/getMediaAuth")
	public String getMediaAuth(ModelMap model) {
		model.addAttribute("auths",dal.queryToMapList("select * from Lookups where CatFK=1 and IsDeleted=0"));
		return "components/mediauath";
	}
	
	@RequestMapping(value = "/allmedia")
	public String allmedia(ModelMap model,HttpServletRequest request) {
		String types="",authers="",suars="";
		boolean ispost=false;
		if(html.isPost()){
			String where="";
			
			types=request.getParameter("typesselected");
			authers=request.getParameter("authersselected");
			suars=request.getParameter("suarsselected");
			
			where +=(html.isNullOrEmpty(types)?"":" and mi.TypeFK in ("+types+")");
			where +=(html.isNullOrEmpty(authers)?"":" and  mi.AutherFK in ("+authers+")");
			where +=(html.isNullOrEmpty(suars)?"":" and  mi.Sura in ("+suars+")");
			
			model.addAttribute("data" ,dal.getMediaInfos(where,true));
			ispost=true;
		}
		
		model.addAttribute("typesselected",types);
		model.addAttribute("authersselected",authers);
		model.addAttribute("suarsselected",suars);
		model.addAttribute("ispost",ispost);
		model.addAttribute("types",dal.queryToMapList("select * from Lookups where CatFK=2 and IsApproved=1 and IsDeleted=0"));
		model.addAttribute("auths",dal.queryToMapList("select * from Lookups where CatFK=1 and IsApproved=1 and IsDeleted=0"));
		model.addAttribute("suars",dal.queryToMapList("select Id,ArName 'Name' from Suras "));
		return "components/allmedia";
	}
	
	@RequestMapping(value = "/addmedia")
	public String allmedia(ModelMap model,String sura,String aya,@Valid MediaInfo mi, BindingResult mibr,@Valid MediaAuthor ma, BindingResult mabr, RedirectAttributes redirectAttrs) {
		if(!session.isLogedin())
			return "/errors/notlogin";
		sura=html.isNullOrEmpty(sura)?"1":sura;
		aya=html.isNullOrEmpty(aya)?"1":aya;
		String toaya=aya;
		if(html.isPost()){
			FormValidation fv=html.validateForm(html.ar("succ1"),mibr,mabr);
			sura=mi.getSura()+"";
			aya=mi.getFromAya()+"";
			toaya=mi.getToAya()+"";
			model.addAttribute("errors",fv);
			if(fv.isSuccess()){

				if(!html.isInteger(ma.getMediaAutherName())){
					mi.setAutherFK(dal.addNewLookups(ma.getMediaAutherName(), 1).getId());
				}else{
					mi.setAutherFK(Integer.parseInt(ma.getMediaAutherName()));
				}
				
				if(!html.isInteger(ma.getMediaType())){
					mi.setTypeFK(dal.addNewLookups(ma.getMediaType(), 2).getId());
				}else{
					mi.setTypeFK(Integer.parseInt(ma.getMediaType()));
				}
				
				mi.setUserFK(session.getUser().getId());
				dal.saveOrUpdate(mi);
				model.addAttribute("urladdnote",html.ar("urladdnote"));
				
				redirectAttrs.addFlashAttribute("_Message" ,fv.getMessage());
				return "redirect:/" ;
			}
		}
		model.addAttribute("authsloo",dal.queryToMapList("select * from Lookups where CatFK=1 and IsDeleted=0"));
		model.addAttribute("typesloo",dal.queryToMapList("select * from Lookups where CatFK=2 and IsDeleted=0"));
		model.addAttribute("mi",mi);
		model.addAttribute("ma",ma);
		model.addAttribute("sura",sura);
		model.addAttribute("toaya",toaya);
		model.addAttribute("aya",aya);
		return "/components/addmedia";
	}
	
	@RequestMapping(value = "/addmediaau")
	public String addmediaau(ModelMap model,@Valid MediaInfo mi, BindingResult mibr,@Valid MediaAuthor ma, BindingResult mabr, RedirectAttributes redirectAttrs) {
		if(!session.isLogedin())
			return "/errors/notlogin";
		
		if(html.isPost()){
			FormValidation fv=html.validateForm(html.ar("succ1"),mibr,mabr);
			model.addAttribute("errors",fv);
			if(fv.isSuccess()){

				if(!html.isInteger(ma.getMediaAutherName())){
					mi.setAutherFK(dal.addNewLookups(ma.getMediaAutherName(), 1).getId());
				}else{
					mi.setAutherFK(Integer.parseInt(ma.getMediaAutherName()));
				}
				
				if(!html.isInteger(ma.getMediaType())){
					mi.setTypeFK(dal.addNewLookups(ma.getMediaType(), 2).getId());
				}else{
					mi.setTypeFK(Integer.parseInt(ma.getMediaType()));
				}
				mi.setUserFK(session.getUser().getId());
				dal.saveOrUpdate(mi);
				model.addAttribute("urladdnote",html.ar("urladdnote"));
				
				redirectAttrs.addFlashAttribute("_Message" ,fv.getMessage());
				return "redirect:/" ;
			}
		}
		model.addAttribute("authsloo",dal.queryToMapList("select * from Lookups where CatFK=1 and IsDeleted=0"));
		model.addAttribute("typesloo",dal.queryToMapList("select * from Lookups where CatFK=2 and IsDeleted=0"));
		model.addAttribute("mi",mi);
		model.addAttribute("ma",ma);
		return "/components/addmediaau";
	}
	
	@RequestMapping(value = "/quranverses/{p1}/{p2}/{p3}")
	public void quranverses(ModelMap model,@PathVariable(value = "p1") String p1,
			@PathVariable(value = "p2") String p2,@PathVariable(value = "p3") String p3,HttpServletRequest request,
            HttpServletResponse response) throws IOException {
		String path ="/home/media/quranverses/"+p1+"/"+p2+"/"+p3.replaceAll(".mp3", "")+".mp3";

		
		new Download().DownloadFile(path, request, response);
	}

	@RequestMapping(value = "/quranverses/{p1}/{p2}/{p3}/{p4}")
	public void quranverses2(ModelMap model,@PathVariable(value = "p1") String p1,
			@PathVariable(value = "p2") String p2,
			@PathVariable(value = "p4") String p4,
			@PathVariable(value = "p3") String p3,HttpServletRequest request,
            HttpServletResponse response) throws IOException {
		String path ="/home/media/quranverses/"+p1+"/"+p2+"/"+p3+"/"+p4.replaceAll(".mp3", "")+".mp3";

		
		new Download().DownloadFile(path, request, response);
	}
	
	@RequestMapping(value="/xml/ads")
	public String ads() {

		return "/home/ads";

	}
	
	@RequestMapping(value="/xml/info")
	public String info() {

		return "/home/info";

	}
	
}
