package com.tools.is;


import com.tools.is.config.zookeeper.ZookeeperParam;
import com.tools.is.listeners.ApplicationStartup;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({ZookeeperParam.class})
public class StartApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(StartApplication.class);
        application.addListeners(new ApplicationStartup());
        application.run(args);
    }
}
