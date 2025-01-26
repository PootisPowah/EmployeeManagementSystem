package com.practice.Employee.Management2.dto;

public record EmployeeFilterDto (
        String firstName,
        String lastName,
        String email,
        String phone,
        String salaryMin,
        String salaryMax,
        String gender,
        String departmentName,
        String roleName
) {
}
