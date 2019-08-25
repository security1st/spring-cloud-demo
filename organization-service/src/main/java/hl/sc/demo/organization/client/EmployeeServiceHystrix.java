package hl.sc.demo.organization.client;

import hl.sc.demo.organization.model.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: hl
 * @created: 2019-08-24 13:46
 **/
@Component
@Slf4j
class EmployeeServiceHystrix implements EmployeeClient {
    @Override
    public List<Employee> findByOrganization(Long organizationId) {
        log.warn("Fallback Employee find: organizationId={}", organizationId);
        return new ArrayList<>();
    }
}
