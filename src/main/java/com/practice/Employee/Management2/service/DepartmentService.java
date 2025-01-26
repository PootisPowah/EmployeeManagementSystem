package com.practice.Employee.Management2.service;

import com.practice.Employee.Management2.dto.DepartmentDto;
import com.practice.Employee.Management2.dto.DepartmentDtoGet;
import com.practice.Employee.Management2.entity.Department;

import java.util.List;

public interface DepartmentService {
    DepartmentDtoGet getDepartmentById(long id);
    List<DepartmentDtoGet> getAllDepartments();
    DepartmentDtoGet addDepartment(DepartmentDto departmentDto);
    DepartmentDtoGet updateDepartment(DepartmentDto departmentDto, long id);
    void deleteDepartment(long id);

    DepartmentDtoGet convertToDto(Department department);
}
