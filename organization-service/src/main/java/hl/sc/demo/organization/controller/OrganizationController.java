package hl.sc.demo.organization.controller;

import hl.sc.demo.organization.client.DepartmentClient;
import hl.sc.demo.organization.client.EmployeeClient;
import hl.sc.demo.organization.model.Organization;
import hl.sc.demo.organization.repository.OrganizationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class OrganizationController {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrganizationController.class);
	
	@Autowired
    OrganizationRepository repository;
	@Autowired
	DepartmentClient departmentClient;
	@Autowired
	EmployeeClient employeeClient;
	
	@PostMapping
	public Mono<Organization> add(@RequestBody Organization organization) {
		LOGGER.info("Organization add: {}", organization);
		return repository.add(organization);
	}
	
	@GetMapping
	public Flux<Organization> findAll() {
		LOGGER.info("Organization find");
		return repository.findAll();
	}
	
	@GetMapping("/{id}")
	public Mono<Organization> findById(@PathVariable("id") Long id) {
		LOGGER.info("Organization find: id={}", id);
		return repository.findById(id);
	}

	@GetMapping("/{id}/with-departments")
	public Mono<Organization> findByIdWithDepartments(@PathVariable("id") Long id) {
		LOGGER.info("Organization find: id={}", id);
		return repository.findById(id).doOnSuccess(d -> d.setDepartments(departmentClient.findByOrganization(d.getId()).collectList().block()));
	}
	
	@GetMapping("/{id}/with-departments-and-employees")
	public Mono<Organization> findByIdWithDepartmentsAndEmployees(@PathVariable(
			"id") Long id) {
		LOGGER.info("Organization find: id={}", id);
		return repository.findById(id).doOnSuccess(d -> d.setDepartments(departmentClient.findByOrganizationWithEmployees(d.getId()).collectList().block()));
	}
	
	@GetMapping("/{id}/with-employees")
	public Mono<Organization> findByIdWithEmployees(@PathVariable("id") Long id) {
		LOGGER.info("Organization find: id={}", id);
		return repository.findById(id).doOnSuccess(d -> d.setEmployees(employeeClient.findByOrganization(d.getId()).collectList().block()));
	}
	
}
