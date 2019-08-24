package hl.sc.demo.department.client;

import hl.sc.demo.department.model.Employee;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Flux;

@FeignClient(name = "employee-service")
public interface EmployeeClient {

	@GetMapping("/department/{departmentId}")
    Flux<Employee> findByDepartment(@PathVariable("departmentId") Long departmentId);
	
}
