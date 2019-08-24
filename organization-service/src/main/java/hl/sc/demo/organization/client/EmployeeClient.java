package hl.sc.demo.organization.client;

import hl.sc.demo.organization.model.Employee;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Flux;

@FeignClient(name = "employee-service", fallback = EmployeeServiceHystrix.class)
public interface EmployeeClient {

	@GetMapping("/organization/{organizationId}")
	Flux<Employee> findByOrganization(@PathVariable("organizationId") Long organizationId);
	
}
