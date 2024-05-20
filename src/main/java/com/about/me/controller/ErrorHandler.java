package com.about.me.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ErrorHandler implements ErrorController{
	
	@RequestMapping("/error")
	public String handleError(HttpServletRequest request) {
//		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
//		if (status != null) {
//			int statusCode = Integer.valueOf(status.toString());
//			
//			if(statusCode == HttpStatus.NOT_FOUND.value()) {
//				return "404";
//			} 
//		}
		return "error/error";
	}
}
