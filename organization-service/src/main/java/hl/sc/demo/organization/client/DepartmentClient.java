package hl.sc.demo.organization.client;

import hl.sc.demo.organization.model.Department;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Flux;

@FeignClient(name = "department-service")
public interface DepartmentClient {

	@GetMapping("/organization/{organizationId}")
	Flux<Department> findByOrganization(@PathVariable("organizationId") Long organizationId);
	
	@GetMapping("/organization/{organizationId}/with-employees")
	Flux<Department> findByOrganizationWithEmployees(@PathVariable(
			"organizationId") Long organizationId);
	
}
