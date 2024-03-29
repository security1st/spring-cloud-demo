package hl.sc.demo.employee.repository;

import hl.sc.demo.employee.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

@Component
public class EmployeeRedisRepository {

    @Autowired
    private ReactiveRedisOperations<String, Employee> reactiveRedisOperations;

    public Mono<Employee> add(Employee employee) {
        return findAll().count().map(count -> {
            employee.setId(BigInteger.valueOf(count + 1));
            return employee;
        }).then(
                reactiveRedisOperations.opsForList()
                                       .rightPush(Employee.EMPLOYEE, employee)
                                       .then(Mono.just(employee))
        );
    }

    public Mono<Employee> findById(Long id) {
        return reactiveRedisOperations.opsForList()
                                      .range(Employee.EMPLOYEE, 0, -1)
                                      .filter(e -> id.equals(e.getId()))
                                      .next();
    }

    public Flux<Employee> findAll() {
        return reactiveRedisOperations.opsForList()
                                      .range(Employee.EMPLOYEE, 0, -1);
    }

    public Flux<Employee> findByDepartment(Long departmentId) {
        return reactiveRedisOperations.opsForList()
                                      .range(Employee.EMPLOYEE, 0, -1)
                                      .filter(e -> departmentId.equals(e.getDepartmentId()));
    }

    public Flux<Employee> findByOrganization(Long organizationId) {
        return reactiveRedisOperations.opsForList()
                                      .range(Employee.EMPLOYEE, 0, -1)
                                      .filter(e -> organizationId.equals(e.getOrganizationId()));
    }
}
