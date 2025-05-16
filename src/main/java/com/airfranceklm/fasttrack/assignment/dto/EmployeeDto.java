package com.airfranceklm.fasttrack.assignment.dto;

import com.airfranceklm.fasttrack.assignment.Entity.Employee;
import lombok.Data;

@Data
public class EmployeeDto {

    private String employeeId;

    private String name;

    public EmployeeDto(Employee emp) {
        this.employeeId = emp.getEmployeeId();
        this.name = emp.getName();
    }
    public EmployeeDto() {}
}
