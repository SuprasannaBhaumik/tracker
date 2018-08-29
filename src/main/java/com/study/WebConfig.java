package com.study;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
public class WebConfig implements WebApplicationInitializer{

	private AnnotationConfigWebApplicationContext getContext() {
		AnnotationConfigWebApplicationContext wac = new AnnotationConfigWebApplicationContext();
		wac.setConfigLocation("com.study.WebConfig");
		return wac;
	}

	public void onStartup(ServletContext servletContext) throws ServletException {
		WebApplicationContext wac = getContext();
		servletContext.addListener(new ContextLoaderListener(wac));
		ServletRegistration.Dynamic dispatcher = 
				servletContext.addServlet("DispatcherServlet", new DispatcherServlet(wac));
		dispatcher.addMapping("*.html");
		dispatcher.setLoadOnStartup(1);
	}

}
