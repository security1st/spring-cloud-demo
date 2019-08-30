package hl.sc.demo.employee.model;


import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
public class Employee {

    public static final String EMPLOYEE = "employee";

    private BigInteger id;
    private Long organizationId;
    private Long departmentId;
    private String name;
    private int age;
    private String position;

    public Employee() {

    }

    public Employee(Long organizationId, Long departmentId, String name, int age, String position) {
        this.organizationId = organizationId;
        this.departmentId = departmentId;
        this.name = name;
        this.age = age;
        this.position = position;
    }

    @Override
    public String toString() {
        return "Employee [id=" + id + ", organizationId=" + organizationId + ", departmentId=" + departmentId
                + ", name=" + name + ", position=" + position + "]";
    }

}
