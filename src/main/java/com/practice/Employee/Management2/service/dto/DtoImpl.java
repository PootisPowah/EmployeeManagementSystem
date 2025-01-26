package com.practice.Employee.Management2.service.dto;

import com.practice.Employee.Management2.dto.DepartmentDtoGet;
import com.practice.Employee.Management2.dto.EmployeeDto;
import com.practice.Employee.Management2.entity.Department;
import com.practice.Employee.Management2.entity.Employee;
import com.practice.Employee.Management2.service.DepartmentService;
import org.springframework.stereotype.Service;

@Service
public class DtoImpl {
    private final DepartmentService departmentService;

    public DtoImpl(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    public EmployeeDto convertToDto(Employee employee){
        DepartmentDtoGet departmentDto = departmentService.convertToDto(employee.getDepartment());
        return new EmployeeDto(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail(),
                employee.getGender(),
                employee.getPhone(),
                employee.getSalary(),
                departmentDto,
                employee.getRole());

    }

    public  DepartmentDtoGet convertToDto(Department department) {
        return new DepartmentDtoGet(
                department.getId(),
                department.getDepartmentName(),
                department.getManager()!= null ? department.getManager().getId() : 0);


    }
}
