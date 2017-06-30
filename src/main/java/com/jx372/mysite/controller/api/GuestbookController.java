package com.jx372.mysite.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jx372.mysite.dto.JSONResult;
import com.jx372.mysite.service.GuestbookService;
import com.jx372.mysite.vo.GuestbookVo;

@Controller( "guestbookAPIController" )
@RequestMapping( "/guestbook/api" )
public class GuestbookController {
	
	@Autowired
	private GuestbookService guestbookService;
	
	@ResponseBody
	@RequestMapping( "/list" )
	public JSONResult list( 
		@RequestParam( value="sno", required=true, defaultValue="0" ) Long startNo ) {
		List<GuestbookVo> list = guestbookService.getMessageList( startNo );
		return JSONResult.success( list );
	}
	
	@ResponseBody	
	@RequestMapping( value="/add", method=RequestMethod.POST )
	public JSONResult add( @ModelAttribute GuestbookVo vo ) {
		guestbookService.writeMessage( vo );
		return JSONResult.success( vo );
	}	
}
