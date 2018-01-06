package com.notes.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class WebApp implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {

		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.register(RestConfig.class, SecurityConfig.class, DataConfiguration.class);

		servletContext.addListener(new ContextLoaderListener(context));

		servletContext.addFilter("springSecurityFilterChain",
								 new DelegatingFilterProxy("springSecurityFilterChain"))
					  .addMappingForUrlPatterns(null, false, "/rest/*");

//		FilterRegistration.Dynamic springSecurityFilterChain =
//				servletContext.addFilter("springSecurityFilterChain", new DelegatingFilterProxy());
//		springSecurityFilterChain.addMappingForUrlPatterns(null, false, "/rest/*");
//		springSecurityFilterChain.setAsyncSupported(true);

		ServletRegistration.Dynamic dispatcher = servletContext
				.addServlet("rest-servlet", new DispatcherServlet(context));

		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/rest/*");
	}
}
