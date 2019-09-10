package hl.sc.demo.employee.controller;

import hl.sc.demo.employee.model.Employee;
import hl.sc.demo.employee.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

@RefreshScope
@RestController
public class EmployeeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    EmployeeService employeeService;
    @Value("${name}")
    String name;

    @PostMapping("/")
    public Mono<Employee> add(@RequestBody Employee employee) {
        LOGGER.info("Employee add: {}", employee);
        return employeeService.add(employee);
    }

    @DeleteMapping("/{id}")
    public Mono<BigInteger> delete(@PathVariable("id") BigInteger id) {
        LOGGER.info("Employee delete: id={}", id);
        return employeeService.delete(id);
    }

    @GetMapping("/{id}")
    public Mono<Employee> findById(@PathVariable("id") BigInteger id) {
        LOGGER.info("Employee find: id={}", id);
        return employeeService.findById(id);
    }

    @GetMapping("/")
    public Flux<Employee> findAll() {
        LOGGER.info("Employee find");
        return employeeService.findAll();
    }

    @GetMapping("/department/{departmentId}")
    public Flux<Employee> findByDepartment(@PathVariable("departmentId") Long departmentId) {
        LOGGER.info("Employee find: departmentId={}", departmentId);
        return employeeService.findByDepartment(departmentId);
    }

    @GetMapping("/organization/{organizationId}")
    public Flux<Employee> findByOrganization(@PathVariable("organizationId") Long organizationId) {
        LOGGER.info("Employee find: organizationId={}", organizationId);
        return employeeService.findByOrganization(organizationId);
    }

    @GetMapping("/hi")
    public Mono<String> hi() {
        return Mono.just("hi, " + name);
    }
}
