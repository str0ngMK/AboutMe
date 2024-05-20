package com.about.me.config;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	
	@Autowired
	private ApplicationConfig applicationConfig;
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		System.out.println("img repo: " + applicationConfig.getImgRepoPath());
		registry.addResourceHandler("/file/**").addResourceLocations("file:///" + applicationConfig.getImgRepoPath());
	}
	

}
