package com.practice.Employee.Management2.service;

import com.practice.Employee.Management2.dto.EmployeeDto;
import com.practice.Employee.Management2.dto.EmployeeFilterDto;
import com.practice.Employee.Management2.dtoClass.EmployeeDtoClass;
import com.practice.Employee.Management2.dtoClass.EmployeePaginationResponseClass;
import com.practice.Employee.Management2.entity.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    EmployeeDtoClass getEmployeeById(long id);
    EmployeePaginationResponseClass getAllEmployees(int pageNo, int pageSize, String sortProperty);
    EmployeeDtoClass addEmployee(Employee employee);
    EmployeeDtoClass updateEmployee(Employee employee);
    void deleteEmployee(long id);

    List<EmployeeDto> getEmployeesByDepartmentName(String departmentName);

    Optional<EmployeeDto> getEmployeeByExample(Employee employee);
    List<Employee> getEmployeesByCustomMatcher(String firstName, String department);

    List<Employee> getEmployeesByFilterCriteria(EmployeeFilterDto filterDto);

}
