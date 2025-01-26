package com.practice.Employee.Management2.service.impl;

import com.practice.Employee.Management2.dto.DepartmentDto;
import com.practice.Employee.Management2.dto.DepartmentDtoGet;
import com.practice.Employee.Management2.entity.Department;
import com.practice.Employee.Management2.entity.Employee;
import com.practice.Employee.Management2.exception.ResourceNotFoundException;
import com.practice.Employee.Management2.repository.DepartmentRepository;
import com.practice.Employee.Management2.repository.EmployeeRepository;
import com.practice.Employee.Management2.service.DepartmentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository,EmployeeRepository employeeRepository) {
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public DepartmentDtoGet getDepartmentById(long id) {
        Department department = departmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Department with id " + id + " not found"));
        return convertToDto(department);
    }

    @Override
    public List<DepartmentDtoGet> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        return departments.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public DepartmentDtoGet addDepartment(DepartmentDto departmentDto) {

        Department department = new Department();
        department.setDepartmentName(departmentDto.departmentName());

        if(departmentDto.managerId() != null ){
            Employee manager = employeeRepository.findById(departmentDto.managerId()).orElseThrow(() -> new RuntimeException("Manager not found"));
            department.setManager(manager);
        }
        else{
            department.setManager(null);
        }
        Department newDepartment = departmentRepository.save(department);
        return convertToDto(newDepartment);
    }

    @Override
    public DepartmentDtoGet updateDepartment(DepartmentDto departmentDto, long id) {
        Department department = departmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Department not found"));

        department.setDepartmentName(departmentDto.departmentName());
        Employee manager = employeeRepository.findById(departmentDto.managerId()).orElseThrow(() -> new RuntimeException("Manager not found"));
        department.setManager(manager);
        Department updatedDepartment = departmentRepository.save(department);
        return convertToDto(updatedDepartment);
    }

    @Override
    public void deleteDepartment(long id) {
        departmentRepository.deleteById(id);
    }

    public  DepartmentDtoGet convertToDto(Department department) {
       return new DepartmentDtoGet(
               department.getId(),
               department.getDepartmentName(),
               department.getManager()!= null ? department.getManager().getId() : 0);


    }

}
