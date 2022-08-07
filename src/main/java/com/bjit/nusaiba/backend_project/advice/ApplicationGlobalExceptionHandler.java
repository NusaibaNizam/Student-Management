package com.bjit.nusaiba.backend_project.advice;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;




@ControllerAdvice
class ApplicationGlobalExceptionHandler{


	@ExceptionHandler(Exception.class)
	public String handleException() {

		return "/exception";
	}
	
}