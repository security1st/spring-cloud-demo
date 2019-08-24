package hl.sc.demo.department.repository;

import hl.sc.demo.department.model.Department;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

public class DepartmentRepository {

	private List<Department> departments = new ArrayList<>();

	public Mono<Department> add(Department department) {
		return Mono.just(department)
				.doOnSuccess(d -> {
					d.setId((long) (departments.size() + 1));
					departments.add(d);
				});
	}

	public Mono<Department> findById(Long id) {
		return Flux.fromIterable(departments)
				.filter(a -> a.getId().equals(id)).next();
	}

	public Flux<Department> findAll() {
		return Flux.fromIterable(departments);
	}

	public Flux<Department> findByOrganization(Long organizationId) {
		return Flux.fromIterable(departments)
				.filter(a -> a.getOrganizationId().equals(organizationId));
	}
	
}
