package com.incedo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CitiAbTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(CitiAbTestApplication.class, args);
	}

}

/*
@SpringBootApplication
public class CitiAbTestApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(CitiAbTestApplication.class, args);
	}
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(CitiAbTestApplication.class);
    }
}*/
