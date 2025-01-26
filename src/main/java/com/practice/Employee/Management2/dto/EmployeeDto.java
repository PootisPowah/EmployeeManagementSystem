package com.practice.Employee.Management2.dto;

import com.practice.Employee.Management2.entity.Role;

public record EmployeeDto (
        long id,
        String firstName,
        String lastName,
        String email,
        String gender,
        String phone,
        Double salary,
        DepartmentDtoGet department,
        Role role){
}
