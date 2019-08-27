package hl.sc.demo.employee.repository;

import hl.sc.demo.employee.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class EmployeeRepository {
    public static final String EMPLOYEE = "employee";

    @Autowired
    private ReactiveRedisOperations<String, Employee> reactiveRedisOperations;

    public Mono<Employee> add(Employee employee) {
        return reactiveRedisOperations.opsForList().rightPush(EMPLOYEE, employee)
                .then(Mono.just(employee));
    }

    public Mono<Employee> findById(Long id) {
        return reactiveRedisOperations.opsForList()
                                      .range(EMPLOYEE, 0, -1)
                                      .filter(e -> id.equals(e.getId()))
                                      .next();
    }

    public Flux<Employee> findAll() {
        return reactiveRedisOperations.opsForList().range(EMPLOYEE, 0, -1);
    }

    public Flux<Employee> findByDepartment(Long departmentId) {
        return reactiveRedisOperations.opsForList().range(EMPLOYEE, 0, -1)
                                      .filter(e -> departmentId.equals(e.getDepartmentId()));
    }

    public Flux<Employee> findByOrganization(Long organizationId) {
        return reactiveRedisOperations.opsForList().range(EMPLOYEE, 0, -1)
                                      .filter(e -> organizationId.equals(e.getOrganizationId()));
    }
}
