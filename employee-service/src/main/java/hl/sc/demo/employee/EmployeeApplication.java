package hl.sc.demo.employee;

import hl.sc.demo.employee.model.Employee;
import hl.sc.demo.employee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import reactor.core.publisher.Flux;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebFlux;

import java.util.stream.Stream;

@SpringBootApplication
@EnableDiscoveryClient
@EnableSwagger2WebFlux
public class EmployeeApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmployeeApplication.class, args);
    }

    @Bean
    public Docket swaggerPersonApi10() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("hl.sc.demo.employee.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(new ApiInfoBuilder().version("1.0").title("Employee API").description("Documentation Employee API v1.0").build());
    }

    @Autowired
    ReactiveRedisConnectionFactory factory;

    @Autowired
    ReactiveRedisOperations<String, Employee> reactiveListOperations;

    @Bean
    EmployeeRepository repository() {
        EmployeeRepository repository = new EmployeeRepository();
        var employeeStream = Stream.of(
                new Employee(1L, 1L, "John Smith", 34, "Analyst"),
                new Employee(1L, 1L, "Darren Hamilton", 37, "Manager"),
                new Employee(1L, 1L, "Tom Scott", 26, "Developer"),
                new Employee(1L, 2L, "Anna London", 39, "Analyst"),
                new Employee(1L, 2L, "Patrick Dempsey", 27, "Developer"),
                new Employee(2L, 3L, "Kevin Price", 38, "Developer"),
                new Employee(2L, 3L, "Ian Scott", 34, "Developer"),
                new Employee(2L, 3L, "Andrew Campton", 30, "Manager"),
                new Employee(2L, 4L, "Steve Franklin", 25,
                        "Developer"),
                new Employee(2L, 4L, "Elisabeth Smith", 30, "Developer"));

        factory.getReactiveConnection()
                .serverCommands()
                .flushAll()
                .thenMany(
                        Flux.fromStream(employeeStream)
                                .flatMap(coffee -> reactiveListOperations.opsForList().rightPush(
                                        EmployeeRepository.EMPLOYEE, coffee)))
                .thenMany(reactiveListOperations.opsForList().range(EmployeeRepository.EMPLOYEE, 0, -1)
                        .map(Employee::getName))
                .subscribe(System.out::println);
        return repository;
    }
}
