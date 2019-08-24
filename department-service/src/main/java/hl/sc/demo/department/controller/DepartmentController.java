package hl.sc.demo.department.controller;

import hl.sc.demo.department.client.EmployeeClient;
import hl.sc.demo.department.model.Department;
import hl.sc.demo.department.repository.DepartmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class DepartmentController {

	private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentController.class);
	
	@Autowired
	DepartmentRepository repository;
	@Autowired
	EmployeeClient employeeClient;
	
	@PostMapping("/")
	public Mono<Department> add(@RequestBody Department department) {
		LOGGER.info("Department add: {}", department);
		return repository.add(department);
	}
	
	@GetMapping("/{id}")
	public Mono<Department> findById(@PathVariable("id") Long id) {
		LOGGER.info("Department find: id={}", id);
		return repository.findById(id);
	}
	
	@GetMapping("/")
	public Flux<Department> findAll() {
		LOGGER.info("Department find");
		return repository.findAll();
	}
	
	@GetMapping("/organization/{organizationId}")
	public Flux<Department> findByOrganization(@PathVariable("organizationId") Long organizationId) {
		LOGGER.info("Department find: organizationId={}", organizationId);
		return repository.findByOrganization(organizationId);
	}
	
	@GetMapping("/organization/{organizationId}/with-employees")
	public Flux<Department> findByOrganizationWithEmployees(@PathVariable(
			"organizationId") Long organizationId) {
		LOGGER.info("Department find: organizationId={}", organizationId);
		var organizations = repository.findByOrganization(organizationId);

		var departmentFlux =
				organizations.map(d -> {
					employeeClient.findByDepartment(d.getId()).collectList().subscribe(d::setEmployees);
					return d;
				});
		return departmentFlux;
		/*return repository.findByOrganization(organizationId)
				.doOnNext(d -> employeeClient.findByDepartment(d.getId()).collectList().subscribe(d::setEmployees));*/
	}
}
