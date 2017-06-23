package com.jx372.mysite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.jx372.mysite.service.FileUploadService;
import com.jx372.mysite.service.SiteService;
import com.jx372.mysite.vo.SiteVo;
import com.jx372.security.Auth;

@Auth( value=Auth.Role.ADMIN )
@Controller
@RequestMapping( "/admin" )
public class AdminController {

	@Autowired
	private SiteService siteService;

	@Autowired
	private FileUploadService fileUploadService;
	
	@RequestMapping( { "", "/main" } )
	public String main( Model model ) {
		SiteVo siteVo = siteService.getSiteInformation();
		
		model.addAttribute( "siteVo", siteVo );
		return "admin/main";
	}

	@RequestMapping( "/main/update" )
	public String updateSite(
		@ModelAttribute SiteVo siteVo,
		@RequestParam( value="file1" ) MultipartFile file1 ) {
		
		String profileURL = fileUploadService.restore( file1 );
		siteVo.setProfileURL( profileURL );
		siteService.updateSiteInformation( siteVo );
		
		return "redirect:/admin/main?result=success";	
	}
	
	@RequestMapping( "/board" )
	public String board() {
		return "admin/board";
	}
	
}
