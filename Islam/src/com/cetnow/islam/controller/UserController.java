package com.cetnow.islam.controller;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cetnow.beans.FormValidation;
import com.cetnow.beans.MediaInfo;
import com.cetnow.db.DAL;
import com.cetnow.util.Html;
import com.cetnow.util.Session;
import com.cetnow.util.SiteConfigrations;
import com.cetnow.util.UserConfiguration;
import com.restfb.DefaultFacebookClient;
import com.restfb.DefaultJsonMapper;
import com.restfb.ETagWebRequestor;
import com.restfb.FacebookClient;
import com.restfb.FacebookClient.AccessToken;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.exception.devicetoken.FacebookDeviceTokenCodeExpiredException;
import com.restfb.exception.devicetoken.FacebookDeviceTokenDeclinedException;
import com.restfb.exception.devicetoken.FacebookDeviceTokenPendingException;
import com.restfb.exception.devicetoken.FacebookDeviceTokenSlowdownException;
import com.restfb.scope.ScopeBuilder;
import com.restfb.scope.UserDataPermissions;
import com.restfb.types.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Simple little @Controller that invokes Facebook and renders the result.
 * The injected {@link Facebook} reference is configured with the required authorization credentials for the current user behind the scenes.
 * @author Keith Donald
 */
@Controller
public class UserController {
	
	@Autowired
	private UserConfiguration conf;
	
	@Autowired
	private DAL dal; 
	
	@Autowired
	private Html html;
	
	@Autowired
	private Session session;

	@RequestMapping(value = "/facebook", method = RequestMethod.GET)
	public String home(Model model) {
		if(session.isLogedin())
			return "redirect:/";
		String appId="989007237829469";
//		String appId="795963180529803";
		ScopeBuilder scopeBuilder = new ScopeBuilder();
		FacebookClient client = new DefaultFacebookClient(Version.VERSION_2_4);
		String loginDialogUrlString = client.getLoginDialogUrl(appId, SiteConfigrations.getSocialLoginRedirect(), scopeBuilder)+"%2Cemail";
		return "redirect:"+loginDialogUrlString;
	}
	
	@RequestMapping(value = "/facebooklogin", method = RequestMethod.GET)
	public String s(String code,ModelMap model, RedirectAttributes redirectAttrs){
		if(session.isLogedin())
			return "redirect:/";
		User user=null;
		String appId="989007237829469";
		String appSecret="f78238cda8aee801850289fa778ecc05";
//		String appId="795963180529803";
//		String appSecret="1b995bb8c8de86223c3cf3c674e7b3be";
		try{
			FacebookClient client = new DefaultFacebookClient(Version.VERSION_2_4);
			String redirectUri=SiteConfigrations.getSocialLoginRedirect();
			AccessToken accessToken=client.obtainUserAccessToken(appId, appSecret, redirectUri, code);
			FacebookClient facebookClient = new DefaultFacebookClient(accessToken.getAccessToken(), appSecret,Version.VERSION_2_4);
			user = facebookClient.fetchObject("me", User.class,Parameter.with("fields","gender,birthday,email,last_name,first_name,picture,timezone"));
		}catch(Exception ex){}
		
		if(user!=null&& !html.isNullOrEmpty(user.getEmail())){
			com.cetnow.beans.User u=new com.cetnow.beans.User();
			u.setFirstName(user.getFirstName());
			u.setLastName(user.getLastName());
			u.setEmail(user.getEmail());
			u.setTypeFK(20);
			if(!html.isNullOrEmpty(user.getGender())){
				String gender=user.getGender().trim().toLowerCase();
				u.setGender(gender.equals("male")?11:gender.equals("female")?13:12);
			}
			
			if(dal.isUnique("Users", "Email", user.getEmail())){
				dal.saveOrUpdate(u);
			}
			String query="select * from Users where Email='"+user.getEmail()+"'";
			session.setUser(dal.getById(dal.queryToMap(query).get("Id"),com.cetnow.beans.User.class));
				
		}else{
			redirectAttrs.addFlashAttribute("_Message" ,html.errorMessage("facebookloginerror", "<li>"+html.ar("facebookloginerrord",html.getHref("/", "clickhear"))+"</li>", null));
		}
		
		return "redirect:/" ;
	}
	
	@RequestMapping(value = "/user/signup")
	public String signup(ModelMap model,@Valid com.cetnow.beans.User u, BindingResult uv, RedirectAttributes redirectAttrs){
		if(session.isLogedin())
			return "redirect:/";
		model.addAttribute("gens" , dal.queryToMapList("select * from Lookups where CatFK=3"));
		if(html.isPost()){
			FormValidation fv=html.validateForm(html.ar("userregsuccessmessage"),uv);
			model.addAttribute("errors", fv);
			model.addAttribute("user", u);
			if(fv.isSuccess()){
				redirectAttrs.addFlashAttribute("_Message" ,fv.getMessage());
				u.setPassword(new BCryptPasswordEncoder().encode(u.getPassword()));
				u.setTypeFK(21);
				dal.saveOrUpdate(u);
				return "redirect:/" ;
			}
		}
		return "user/signup";
	}
	
	@RequestMapping(value = "/user/login")
	public String login(ModelMap model,@Valid com.cetnow.beans.User u, BindingResult uv,HttpServletRequest request){
		if(session.isLogedin())
			return "redirect:/";
		
		FormValidation fv=new FormValidation();
		if(html.isPost()){
			String submit=request.getParameter("login");
			if(submit.equals("normal")){
				model.addAttribute("errors", fv);
				model.addAttribute("user", u);
				Map<String, Object> user=html.isNullOrEmpty(u.getEmail())?null:dal.queryToMap("select * from Users where Email='"+u.getEmail()+"'");
				if(user!=null){
					if(new BCryptPasswordEncoder().matches(u.getPassword(), user.get("Password")+""))
						fv.setSuccess(true);
				}
				if(fv.isSuccess()){
					session.setUser(dal.getById(user.get("Id"),com.cetnow.beans.User.class));
					return "redirect:/" ;
				}else{
					fv.setMessage(html.errorMessage("loginerror", "<li>"+html.ar("loginm")+"</li>", ""));
				}
			}else{
				return "redirect:/facebook";
			}
			
		}
		return "user/login";
	}
	
	@RequestMapping(value = "/user/logout")
	public String logout(){
		session.setUser(null);
		return "redirect:/" ;
	}
	

}

