package com.ronins.googlesenkimsinya;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class GooglesenkimsinyaApplication{

	public static void main(String[] args) {
		SpringApplication.run(GooglesenkimsinyaApplication.class, args);
	}

}


/*
@SpringBootApplication
public class GooglesenkimsinyaApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(GooglesenkimsinyaApplication.class, args);
	}


	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(GooglesenkimsinyaApplication.class);
	}


}
*/