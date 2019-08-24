package hl.sc.demo.eureka;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaClusterApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(EurekaClusterApplication.class).run(args);
    }

}
