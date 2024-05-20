package com.about.me.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

	@Value("${image.repository.path}")
	private String imgRepoPath;
	
	public String getImgRepoPath() {
		if (imgRepoPath.endsWith("/")) {
			return this.imgRepoPath;
		}
		return this.imgRepoPath + "/";
	}
}
