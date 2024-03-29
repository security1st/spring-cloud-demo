package hl.sc.demo.employee.service;

import hl.sc.demo.employee.model.Employee;
import hl.sc.demo.employee.repository.EmployeeMongoRepository;
import hl.sc.demo.employee.repository.EmployeeRedisRepository;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

/**
 * @author: hl
 * @created: 2019-08-30 10:22
 **/
@GraphQLApi
@Service
@Slf4j
public class EmployeeService {
    @Autowired
    EmployeeRedisRepository employeeRedisRepository;
    @Autowired
    EmployeeMongoRepository employeeMongoRepository;
    @Autowired
    private ReactiveRedisOperations<String, Employee> reactiveRedisOperations;

    @GraphQLMutation(name = "addEmployee")
    public Mono<Employee> add(Employee employee) {
        log.info("Employee add: {}", employee);
        return employeeMongoRepository.save(employee);
    }

    @GraphQLMutation(name = "delEmployee")
    public Mono<BigInteger> delete(@GraphQLArgument(name = "deleteId") BigInteger id) {
        log.info("Employee delete: id={}", id);
        String key = Employee.EMPLOYEE + "_" + id;
        return employeeMongoRepository.deleteById(id)
                                      .doOnEach(employee -> reactiveRedisOperations
                                              .opsForValue()
                                              .delete(key)
                                              .block()).then(Mono.just(id));

    }

    @GraphQLQuery(name = "employee")
    public Mono<Employee> findById(BigInteger id) {
        log.info("Employee find: id={}", id);
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

    @GraphQLQuery(name = "employees")
    public Flux<Employee> findAll() {
        log.info("Employee find");
        return employeeRedisRepository.findAll();
    }

    @GraphQLQuery(name = "employees")
    public Flux<Employee> findByDepartment(Long departmentId) {
        log.info("Employee find: departmentId={}", departmentId);
        return employeeRedisRepository.findByDepartment(departmentId);
    }

    @GraphQLQuery(name = "employees")
    public Flux<Employee> findByOrganization(Long organizationId) {
        log.info("Employee find: organizationId={}", organizationId);
        return employeeRedisRepository.findByOrganization(organizationId);
    }
}
