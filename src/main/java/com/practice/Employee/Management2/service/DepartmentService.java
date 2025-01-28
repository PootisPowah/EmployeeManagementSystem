package com.practice.Employee.Management2.service;

import com.practice.Employee.Management2.dto.DepartmentDto;
import com.practice.Employee.Management2.dto.DepartmentDtoGet;
import com.practice.Employee.Management2.dtoClass.DepartmentDtoGetClass;
import com.practice.Employee.Management2.entity.Department;

import java.util.List;

public interface DepartmentService {
    DepartmentDtoGetClass getDepartmentById(long id);
    List<DepartmentDtoGetClass> getAllDepartments();
    DepartmentDtoGetClass addDepartment(DepartmentDto departmentDto);
    DepartmentDtoGetClass updateDepartment(DepartmentDto departmentDto, long id);
    void deleteDepartment(long id);

}
