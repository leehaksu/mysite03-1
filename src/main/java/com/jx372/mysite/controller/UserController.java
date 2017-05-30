package com.jx372.mysite.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.jx372.mysite.service.UserService;
import com.jx372.mysite.vo.UserVo;

@Controller
@RequestMapping( "/user" )
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping( value="/join", method=RequestMethod.GET )
	public String join(){
		return "user/join";
	}
	
	@RequestMapping( value="/join", method=RequestMethod.POST )
	public String join( @ModelAttribute UserVo userVo ){
		userService.join( userVo );
		return "redirect:/user/joinsuccess";
	}
	
	@RequestMapping( value="/login", method=RequestMethod.GET )
	public String login() {
		return "user/login";
	}
	
	@RequestMapping( value="/login", method=RequestMethod.POST )
	public String login(
		HttpSession session,
		Model model,
		@RequestParam( value="email", required=true, defaultValue="" ) String email,
		@RequestParam( value="password", required=true, defaultValue="" ) String password
		) {
		
		UserVo userVo = userService.getUser( email, password );
		if( userVo == null ) {
			model.addAttribute( "result", "fail" );
			return "user/login";
		}
		
		//인증
		session.setAttribute( "authUser", userVo );
		return "redirect:/";
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
	
	@RequestMapping( value="/modify", method=RequestMethod.GET )
	public String modify( HttpSession session, Model model ){

		// 인증여부 체크(접근제한)
		UserVo authUser = (UserVo)session.getAttribute( "authUser" );
		if( authUser == null ) {
			return "redirect:/user/login";
		}
		
		UserVo userVo = userService.getUser( authUser.getNo() );
		model.addAttribute( "userVo", userVo );
		return "user/modify";
	}
	
	@RequestMapping( value="/modify", method=RequestMethod.POST )
	public String modify( HttpSession session, @ModelAttribute UserVo userVo ){

		// 인증여부 체크(접근제한)
		UserVo authUser = (UserVo)session.getAttribute( "authUser" );
		if( authUser == null ) {
			return "redirect:/user/login";
		}
		
		userVo.setNo( authUser.getNo() );
		userService.modifyUser( userVo );
		
		return "redirect:/user/modify?result=success";
	}	

}
