package com.tools.is;


import com.tools.is.listeners.ApplicationStartup;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StartApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(StartApplication.class);
        application.addListeners(new ApplicationStartup());
        application.run(args);
    }
}
