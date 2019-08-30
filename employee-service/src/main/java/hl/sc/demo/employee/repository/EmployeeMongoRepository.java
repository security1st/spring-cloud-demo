package hl.sc.demo.employee.repository;

import hl.sc.demo.employee.model.Employee;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

/**
 * @author: hl
 * @created: 2019-08-30 10:12
 **/
@Repository
public interface EmployeeMongoRepository extends ReactiveMongoRepository<Employee,
        BigInteger> {
}

