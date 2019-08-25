package hl.sc.demo.gateway.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebFlux;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2WebFlux
public class ProxyApi {

    @Autowired
    GatewayProperties properties;

    @Primary
    @Bean
    public SwaggerResourcesProvider swaggerResourcesProvider() {
        return () -> {
            List<SwaggerResource> resources = new ArrayList<>();
            properties.getRoutes()
                    .forEach(route -> resources.add(createResource(route.getUri().getHost(), route.getId(),"2.0")));
            return resources;
        };
    }

    private SwaggerResource createResource(String name, String location, String version) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation("/" + location + "/v2/api-docs");
        swaggerResource.setSwaggerVersion(version);
        return swaggerResource;
    }
}
