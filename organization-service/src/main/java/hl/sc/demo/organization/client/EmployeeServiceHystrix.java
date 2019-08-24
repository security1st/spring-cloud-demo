package hl.sc.demo.organization.client;

import hl.sc.demo.organization.model.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

/**
 * @author: hl
 * @created: 2019-08-24 13:46
 **/
@Component
@Slf4j
class EmployeeServiceHystrix implements EmployeeClient {
    @Override
    public Flux<Employee> findByOrganization(Long organizationId) {
        log.warn("Fallback Employee find: organizationId={}", organizationId);
        return Flux.empty();
    }
}
