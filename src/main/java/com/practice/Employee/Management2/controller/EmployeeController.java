package com.practice.Employee.Management2.controller;

import com.practice.Employee.Management2.dto.EmployeeDto;
import com.practice.Employee.Management2.dto.EmployeeFilterDto;
import com.practice.Employee.Management2.dtoClass.EmployeeDtoClass;
import com.practice.Employee.Management2.dtoClass.EmployeePaginationResponseClass;
import com.practice.Employee.Management2.entity.Employee;
import com.practice.Employee.Management2.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDtoClass> getEmployeeById(@PathVariable int id) {
        EmployeeDtoClass employee = employeeService.getEmployeeById(id);
       if (employee == null) {
           return ResponseEntity.notFound().build();
       }
       return ResponseEntity.ok(employee);
    }

    @GetMapping
    public ResponseEntity<EmployeePaginationResponseClass> getAllEmployees(
            @RequestParam(value= "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize,
            @RequestParam(value = "sortBy", required = false) String sortProperty
            ){
        EmployeePaginationResponseClass employees = employeeService.getAllEmployees(pageNo, pageSize, sortProperty);

        if(employees == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(employees);
    }

    @PostMapping
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody Employee employee){
        EmployeeDto createdEmployee = employeeService.addEmployee(employee);
        return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable long id, @RequestBody Employee employee){
//        EmployeeDto existingEmployee = employeeService.getEmployeeById(id);
//        if(existingEmployee == null){
//            return ResponseEntity.notFound().build();
//        }
//        employee.setId(id);
//        EmployeeDto updatedEmployee = employeeService.updateEmployee(employee);
//        return ResponseEntity.ok(updatedEmployee);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<EmployeeDto> deleteEmployee(@PathVariable long id){
//        EmployeeDto employee = employeeService.getEmployeeById(id);
//        if(employee == null){
//            return ResponseEntity.notFound().build();
//        }
//        employeeService.deleteEmployee(id);
//        return ResponseEntity.noContent().build();
//    }

    @GetMapping("/by-name/{name}")
    public ResponseEntity<List<EmployeeDto>> getEmployeeByDepartmentName(@PathVariable String name){
        List<EmployeeDto> employees = employeeService.getEmployeesByDepartmentName(name);
        if(employees == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(employees);
    }

    @PostMapping("/search/example")
    public Optional<EmployeeDto> getEmployeeByExample(@RequestBody Employee employee){

        return employeeService.getEmployeeByExample(employee);
    }

    @PostMapping("/search/example/custom")
    public List<Employee> getEmployeeByCustomMatcher(@RequestParam String firstName, @RequestParam String departmentName){

        return employeeService.getEmployeesByCustomMatcher(firstName,departmentName);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Employee>> getEmployeeByFilter(EmployeeFilterDto filterDto){
        List<Employee> employeesByFilterCriteria = employeeService.getEmployeesByFilterCriteria(filterDto);
        return ResponseEntity.ok(employeesByFilterCriteria);
    }
}
