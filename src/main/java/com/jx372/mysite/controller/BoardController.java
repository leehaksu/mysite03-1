package com.jx372.mysite.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.jx372.mysite.service.BoardService;
import com.jx372.mysite.vo.BoardVo;

@Controller
@RequestMapping( "/board" )
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@RequestMapping( "" )
	public String index(
		@RequestParam( value="p", required=true, defaultValue="1") Integer page,
		@RequestParam( value="kwd", required=true, defaultValue="") String keyword,
		Model model ) {
		
		Map<String, Object> map = boardService.getMessageList( page, keyword );
		model.addAttribute( "map", map );
		
		return "board/index";
	}
	
	@RequestMapping( "/view/{no}" )
	public String view(
		@PathVariable( "no" ) Long no,
		@RequestParam( value="p", required=true, defaultValue="1") Integer page,
		@RequestParam( value="kwd", required=true, defaultValue="") String keyword,
		Model model ) {
		
		BoardVo boardVo = boardService.getMessage( no );
	
		model.addAttribute( "page", page );
		model.addAttribute( "keyword", keyword );
		model.addAttribute( "boardVo", boardVo );
		
		return "board/view";
	}
	
	@RequestMapping( value="/write", method=RequestMethod.GET )	
	public String write(
		HttpSession session, 	
		@RequestParam( value="p", required=true, defaultValue="1") Integer page,
		@RequestParam( value="kwd", required=true, defaultValue="") String keyword) {
		//인증 체크
		if( session.getAttribute( "authUser" ) == null ) {
			return "redirect:/user/login";
		}
		
		return "board/write";
	}

}