package hl.sc.demo.employee.controller;

import hl.sc.demo.employee.model.Employee;
import hl.sc.demo.employee.repository.EmployeeMongoRepository;
import hl.sc.demo.employee.repository.EmployeeRedisRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

@RefreshScope
@RestController
public class EmployeeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    EmployeeRedisRepository employeeRedisRepository;
    @Autowired
    EmployeeMongoRepository employeeMongoRepository;
    @Value("${name}")
    String name;
    @Autowired
    private ReactiveRedisOperations<String, Employee> reactiveRedisOperations;

    @PostMapping("/")
    public Mono<Employee> add(@RequestBody Employee employee) {
        LOGGER.info("Employee add: {}", employee);
        return employeeMongoRepository.save(employee);
    }

    @GetMapping("/{id}")
    public Mono<Employee> findById(@PathVariable("id") BigInteger id) {
        LOGGER.info("Employee find: id={}", id);
        String key = Employee.EMPLOYEE + "_" + id;
        return reactiveRedisOperations
                .opsForValue()
                .get(key)
                .switchIfEmpty(employeeMongoRepository.findById(id)
                                                      .doOnSuccess(employee ->
                                                              reactiveRedisOperations
                                                                      .opsForValue()
                                                                      .set(key, employee)
                                                                      .block()
                                                      ));
    }

    @GetMapping("/")
    public Flux<Employee> findAll() {
        LOGGER.info("Employee find");
        return employeeRedisRepository.findAll();
    }

    @GetMapping("/department/{departmentId}")
    public Flux<Employee> findByDepartment(@PathVariable("departmentId") Long departmentId) {
        LOGGER.info("Employee find: departmentId={}", departmentId);
        return employeeRedisRepository.findByDepartment(departmentId);
    }

    @GetMapping("/organization/{organizationId}")
    public Flux<Employee> findByOrganization(@PathVariable("organizationId") Long organizationId) {
        LOGGER.info("Employee find: organizationId={}", organizationId);
        return employeeRedisRepository.findByOrganization(organizationId);
    }

    @GetMapping("/hi")
    public Mono<String> hi() {
        return Mono.just("hi, " + name);
    }
}
