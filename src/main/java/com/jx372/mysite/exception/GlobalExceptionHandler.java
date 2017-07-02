package com.jx372.mysite.exception;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jx372.mysite.dto.JSONResult;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler( Exception.class )
	public void handleException(
		HttpServletRequest request,
		HttpServletResponse response,
		Exception e ) throws Exception {
		
		//1. 로깅
		StringWriter errors = new StringWriter();
		e.printStackTrace( new PrintWriter( errors ) );
		
		//2. 안내페이지 가기
		//ModelAndView mav = new ModelAndView();
		//mav.addObject( "uri", request.getRequestURI() );
		//mav.addObject( "exception", errors.toString() );
		//mav.setViewName( "error/exception" );
	
   		String accept = request.getHeader("accept");
		if( accept.matches(".*application/json.*") == true ) {
			
			response.setStatus( HttpServletResponse.SC_OK );
			
			OutputStream out = response.getOutputStream();
			
			out.write( new ObjectMapper().writeValueAsString( JSONResult.error( errors.toString() ) ).getBytes() );
			out.flush();
			out.close();
			
		} else {
			request.setAttribute( "url" , request.getRequestURI() );
			request.setAttribute( "exception", errors.toString() );
			request.getRequestDispatcher( "/WEB-INF/views/error/exception.jsp" ).forward( request, response );
		}
		
		
		
		//return mav;
	}
	
	
}