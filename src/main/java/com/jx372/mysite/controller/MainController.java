package com.jx372.mysite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jx372.mysite.service.SiteService;
import com.jx372.mysite.vo.SiteVo;

@Controller
public class MainController {
	
	@Autowired
	private SiteService siteService; 
	
	@RequestMapping( { "/", "/main" } )
	public String index( Model model ) {
		SiteVo siteVo = siteService.getSiteInformation();
		
		model.addAttribute( "siteVo", siteVo );
		return "main/index";
	}
	
	@ResponseBody
	@RequestMapping( "/hello" )
	public String hello() {
		return "<h1>안녕하세요.</h1>";
	}
}
