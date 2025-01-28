package com.practice.Employee.Management2.service.impl;

import com.practice.Employee.Management2.dto.DepartmentDto;
import com.practice.Employee.Management2.dto.DepartmentDtoGet;
import com.practice.Employee.Management2.dtoClass.DepartmentDtoGetClass;
import com.practice.Employee.Management2.entity.Department;
import com.practice.Employee.Management2.entity.Employee;
import com.practice.Employee.Management2.exception.ResourceNotFoundException;
import com.practice.Employee.Management2.repository.DepartmentRepository;
import com.practice.Employee.Management2.repository.EmployeeRepository;
import com.practice.Employee.Management2.service.DepartmentService;
import com.practice.Employee.Management2.service.dto.DtoImpl;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository, EmployeeRepository employeeRepository, DtoImpl dtoImpl, ModelMapper modelMapper) {
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public DepartmentDtoGetClass getDepartmentById(long id) {
        Department department = departmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Department with id " + id + " not found"));
        return modelMapper.map(department,DepartmentDtoGetClass.class);
    }

    @Override
    public List<DepartmentDtoGetClass> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        return departments.stream()
                .map(list -> modelMapper.map(list, DepartmentDtoGetClass.class))
                .collect(Collectors.toList());
    }

    @Override
    public DepartmentDtoGetClass addDepartment(DepartmentDto departmentDto) {

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
        return modelMapper.map(newDepartment, DepartmentDtoGetClass.class);
    }

    @Override
    public DepartmentDtoGetClass updateDepartment(DepartmentDto departmentDto, long id) {
        Department department = departmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Department not found"));

        department.setDepartmentName(departmentDto.departmentName());
        Employee manager = employeeRepository.findById(departmentDto.managerId()).orElseThrow(() -> new RuntimeException("Manager not found"));
        department.setManager(manager);
        Department updatedDepartment = departmentRepository.save(department);
        return modelMapper.map(updatedDepartment, DepartmentDtoGetClass.class);

    }

    @Override
    public void deleteDepartment(long id) {
        if(!departmentRepository.existsById(id)) {
            throw new RuntimeException("Department with id" + id + " not found");
        }
        departmentRepository.deleteById(id);
    }



}
