package com.jx372.mysite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jx372.security.Auth;

//@Auth( role={ "ADMIN", "USER", "SYS" }, test=10 )
//@Auth( { "ADMIN", "USER", "SYS" } )
//@Auth( "Admin" )

@Auth( value=Auth.Role.ADMIN )
@Controller
@RequestMapping( "/admin" )
public class AdminController {

	@RequestMapping( { "", "/main" } )
	public String main() {
		return "admin/main";
	}
	
	@RequestMapping( "/board" )
	public String board() {
		return "admin/board";
	}
	
}
