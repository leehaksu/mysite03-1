package com.jx372.mysite.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jx372.mysite.service.BoardService;

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

		System.out.println( map );
		
		return "board/index";
	}

}