package com.practice.Employee.Management2.service.impl;

import com.practice.Employee.Management2.dto.DepartmentDtoGet;
import com.practice.Employee.Management2.dto.EmployeeDto;
import com.practice.Employee.Management2.dto.EmployeeFilterDto;
import com.practice.Employee.Management2.dtoClass.EmployeeDtoClass;
import com.practice.Employee.Management2.dtoClass.EmployeePaginationResponseClass;
import com.practice.Employee.Management2.entity.Department;
import com.practice.Employee.Management2.entity.Employee;
import com.practice.Employee.Management2.entity.Role;
import com.practice.Employee.Management2.exception.ResourceNotFoundException;
import com.practice.Employee.Management2.repository.EmployeeRepository;
import com.practice.Employee.Management2.service.DepartmentService;
import com.practice.Employee.Management2.service.EmployeeService;
import com.practice.Employee.Management2.service.dto.DtoImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org. springframework.data.domain.Sort;

import static org.springframework.data.domain.ExampleMatcher.*;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentService departmentService;
    private final EntityManager entityManager;
    private final DtoImpl dtoService;
    private final ModelMapper modelMapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, DepartmentService departmentService, EntityManager entityManager, DtoImpl dtoService, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.departmentService = departmentService;
        this.entityManager = entityManager;
        this.dtoService = dtoService;
        this.modelMapper = modelMapper;
    }

    @Override
    public EmployeeDtoClass getEmployeeById(long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee with id " + id +  " not found"));
        return modelMapper.map(employee, EmployeeDtoClass.class);
    }

    @Override
    public EmployeePaginationResponseClass getAllEmployees(int pageNo, int pageSize, String sortProperty) {
        Pageable pageable = null;
        if(sortProperty != null){
            PageRequest.of(pageNo, pageSize, Sort.Direction.ASC, sortProperty);
        }
        else{
            pageable = PageRequest.of(pageNo, pageSize,Sort.Direction.ASC, "id");
        }
        Page<Employee> employees = employeeRepository.findAll(pageable);
        List<Employee> employeesList = employees.getContent();
        List<EmployeeDtoClass> content =  employeesList.stream()
                .map(list -> modelMapper.map(list,EmployeeDtoClass.class))
                .toList();



        EmployeePaginationResponseClass response = modelMapper.map(employees, EmployeePaginationResponseClass.class);
        response.setContent(content);
        return response;

    }

    @Override
    public EmployeeDto addEmployee(Employee employee) {

        Employee employeeDto = employeeRepository.save(employee);
        return convertToDto(employeeDto);
    }

    @Override
    public EmployeeDto updateEmployee(Employee employee) {
        Employee employeeDto = employeeRepository.save(employee);
        return convertToDto(employeeDto);
    }

    @Override
    public void deleteEmployee(long id) {

        if(!employeeRepository.existsById(id)) {
            throw new RuntimeException("Employee with id" + id + " not found");
        }
        employeeRepository.deleteById(id);
    }

    @Override
    public List<EmployeeDto> getEmployeesByDepartmentName(String departmentName) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<EmployeeDto> query = cb.createQuery(EmployeeDto.class);
        Root<EmployeeDto> employeeRoot = query.from(EmployeeDto.class);

        Join<Employee, Department> departmentJoin = employeeRoot.join("department", JoinType.INNER);

        query.select(employeeRoot).where(cb.equal(departmentJoin.get("departmentName"),departmentName));

        return entityManager.createQuery(query).getResultList();

    }

    //find all employees matching exact criteria
    @Override
    public Optional<EmployeeDto> getEmployeeByExample(Employee employee) {
        Example<Employee> example = Example.of(employee);

        Optional<Employee> employeeOptional = employeeRepository.findOne(example);
        return employeeOptional.map(this::convertToDto);
    }

    //final
    @Override
    public List<Employee> getEmployeesByCustomMatcher(String firstName, String departmentName) {

         Department department = new Department();
        department.setDepartmentName(departmentName);

       Employee employee = new Employee();
       employee.setFirstName(firstName);
       employee.setDepartment(department);

        ExampleMatcher matcher = matching()
                .withIgnoreCase()
                .withStringMatcher(StringMatcher.CONTAINING)
                .withIgnoreNullValues()
                .withMatcher("firstName", match -> match.exact())
                .withMatcher("departmentName", match -> match.contains());

        Example <Employee> example = Example.of(employee, matcher);
        return employeeRepository.findAll(example);
    }

    @Override
    public List<Employee> getEmployeesByFilterCriteria(EmployeeFilterDto filterDto) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> query = cb.createQuery(Employee.class);
        Root<Employee> employee = query.from(Employee.class);

        List<Predicate> predicates = new ArrayList<>();

        if(filterDto.firstName() != null && !filterDto.firstName().isEmpty()) {
            predicates.add(cb.like(cb.lower(employee.get("firstName")), "%" +  filterDto.firstName().toLowerCase() + "%"));
        }

        if(filterDto.lastName() != null && !filterDto.lastName().isEmpty()) {
            predicates.add(cb.like(cb.lower(employee.get("lastName")),"%" + filterDto.lastName().toLowerCase() + "%"));
        }
        if(filterDto.email() != null && !filterDto.email().isEmpty()){
            predicates.add(cb.like(cb.lower(employee.get("email")), "%" + filterDto.email() + "%"));
        }
        if(filterDto.phone() != null && !filterDto.phone().isEmpty()){
            predicates.add(cb.equal(employee.get("phone"),filterDto.phone()));
        }
        if(filterDto.salaryMin() != null && !filterDto.salaryMin().isEmpty()){
            predicates.add(cb.greaterThanOrEqualTo(employee.get("salary"),filterDto.salaryMin()));
        }
        if(filterDto.salaryMax() != null && !filterDto.salaryMax().isEmpty()){
            predicates.add(cb.lessThanOrEqualTo(employee.get("salary"),filterDto.salaryMax()));
        }
        if(filterDto.gender() != null && !filterDto.gender().isEmpty()){
            predicates.add(cb.equal(employee.get("gender"), filterDto.gender()));
        }
        if(filterDto.departmentName() != null && !filterDto.departmentName().isEmpty()){
            Join<Employee,Department> department = employee.join("department");
            predicates.add(cb.like(cb.lower(department.get("departmentName")),"%" + filterDto.departmentName().toLowerCase() + "%"));
        }
        if(filterDto.roleName() != null && !filterDto.roleName().isEmpty()){
            Join<Employee, Role> role = employee.join("role");
            predicates.add(cb.like(cb.lower(role.get("roleName")),"%" + filterDto.roleName().toLowerCase() + "%"));
        }
        query.where(predicates.toArray(new Predicate[0]));
        return entityManager.createQuery(query).getResultList();
    }
    //postman offline
    //add in separate service
    //mapstruct, model mapper
    //global exception handler
    public  EmployeeDto convertToDto(Employee employee){
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
}
