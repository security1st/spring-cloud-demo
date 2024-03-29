package hl.sc.demo.department;

import hl.sc.demo.department.model.Department;
import hl.sc.demo.department.repository.DepartmentRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebFlux;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableSwagger2WebFlux
public class DepartmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(DepartmentApplication.class, args);
    }

    @Bean
    public Docket swaggerPersonApi10() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("hl.sc.demo.department.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(new ApiInfoBuilder().version("1.0").title("Department API").description("Documentation Department API v1.0").build());
    }

    @Bean
    DepartmentRepository repository() {
        DepartmentRepository repository = new DepartmentRepository();
        repository.add(new Department(1L, "Development")).block();
        repository.add(new Department(1L, "Operations")).block();
        repository.add(new Department(2L, "Development")).block();
        repository.add(new Department(2L, "Operations")).block();
        return repository;
    }

}
