package com.study;

import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages="com.study")
public class WebConfig extends WebMvcConfigurerAdapter implements WebApplicationInitializer {

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
		dispatcher.addMapping("*.pdf");
		dispatcher.setLoadOnStartup(1);
	}

	//mapping the jsps using the prefix and suffix.
	//view resolver
	//we can have multiple view types in our Spring app
	//using ContentNegotiatingViewResolver
	@Bean
	public InternalResourceViewResolver getInternalResourceViewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/jsp/");
		resolver.setSuffix(".jsp");
		return resolver;
	}

	//required for internationalisation
	//basename is the name of the property file
	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource rms = new ResourceBundleMessageSource();
		rms.setBasename("messages");
		return rms;
	}
	
	//required for internationalization
	//sets the default locale
	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver slr = new SessionLocaleResolver();
		slr.setDefaultLocale(Locale.ENGLISH);
		return slr;
	}

	//will look for our parameter that we have exposed to look for changes
	//in case of locale changes we need to invoke this
	//this method has come from WebMvcConfigurerAdapter
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
		interceptor.setParamName("language");
		registry.addInterceptor(interceptor);
	}
	
	//
	//this method is overridden from WebMvcConfigurerAdapter
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/reports/**")
				.addResourceLocations("/WEB-INF/pdf/");
	}
	
}
