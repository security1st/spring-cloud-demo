package hl.sc.demo.employee.repository;

import hl.sc.demo.employee.model.Employee;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository {

    private List<Employee> employees = new ArrayList<>();

    public Mono<Employee> add(Employee employee) {
        return Mono.just(employee)
                .map(e -> {
                    e.setId((long) (employees.size() + 1));
                    employees.add(e);
                    return e;
                });
    }

    public Mono<Employee> findById(Long id) {
        return Flux.fromIterable(employees)
                .filter(e -> e.getId().equals(id)).next();
    }

    public Flux<Employee> findAll() {
        return Flux.fromIterable(employees);
    }

    public Flux<Employee> findByDepartment(Long departmentId) {
        return Flux.fromIterable(employees)
                .filter(e -> e.getDepartmentId().equals(departmentId));
    }

    public Flux<Employee> findByOrganization(Long organizationId) {
        return Flux.fromIterable(employees)
                .filter(e -> e.getOrganizationId().equals(organizationId));
    }

}
