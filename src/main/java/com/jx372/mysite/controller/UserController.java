package com.jx372.mysite.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.jx372.mysite.service.UserService;
import com.jx372.mysite.vo.UserVo;
import com.jx372.security.Auth;


@Controller
@RequestMapping( "/user" )
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping( value="/join", method=RequestMethod.GET )
	public String join(){
		return "user/join";
	}

//	@ResponseBody
//	@RequestMapping( value="/join", method=RequestMethod.POST )
//	public String join( @RequestBody String requestBody ){
//		return requestBody;
//	}
	
	@RequestMapping( value="/join", method=RequestMethod.POST )
	public String join( @ModelAttribute UserVo userVo ){
		userService.join( userVo );
		return "redirect:/user/joinsuccess";
	}
	
	@RequestMapping( value="/login", method=RequestMethod.GET )
	public String login() {
		return "user/login";
	}
	
	@RequestMapping( "/logout" )
	public String logout( HttpSession session ) {
		session.removeAttribute( "authUser" );
		session.invalidate();
		return "redirect:/";
	}
	
	@RequestMapping( "/joinsuccess" )
	public String joinsuccess(){
		return "user/joinsuccess";
	}
	
	@Auth
	@RequestMapping( value="/modify", method=RequestMethod.GET )
	public String modify( HttpSession session, Model model ){

		UserVo authUser = (UserVo)session.getAttribute( "authUser" );
		
		UserVo userVo = userService.getUser( authUser.getNo() );
		model.addAttribute( "userVo", userVo );
		return "user/modify";
	}
	
	@Auth
	@RequestMapping( value="/modify", method=RequestMethod.POST )
	public String modify( HttpSession session, @ModelAttribute UserVo userVo ){

		UserVo authUser = (UserVo)session.getAttribute( "authUser" );
		userVo.setNo( authUser.getNo() );
		userService.modifyUser( userVo );
		
		return "redirect:/user/modify?result=success";
	}	

}
