package com.jx372.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target( { ElementType.METHOD, ElementType.TYPE } )
@Retention( RetentionPolicy.RUNTIME )
public @interface Auth {
	String[] role() default "USER";
	int test() default 1;
	Role value() default Role.USER;
	
	public enum Role { ADMIN, USER }
}