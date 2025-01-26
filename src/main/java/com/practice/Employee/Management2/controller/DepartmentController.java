package com.practice.Employee.Management2.controller;

import com.practice.Employee.Management2.dto.DepartmentDto;
import com.practice.Employee.Management2.dto.DepartmentDtoGet;
import com.practice.Employee.Management2.entity.Department;
import com.practice.Employee.Management2.service.DepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDtoGet> getDepartmentById(@PathVariable int id) {
        DepartmentDtoGet department = departmentService.getDepartmentById(id);
       if (department == null) {
           return ResponseEntity.notFound().build();
       }
       return ResponseEntity.ok(department);
    }

    @GetMapping
    public ResponseEntity<List<DepartmentDtoGet>> getAllDepartments(){
        List<DepartmentDtoGet> department = departmentService.getAllDepartments();

        if(department == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(department);
    }

    @PostMapping
    public ResponseEntity<DepartmentDtoGet> createDepartment(@RequestBody DepartmentDto departmentDto){
        DepartmentDtoGet createdDepartment = departmentService.addDepartment(departmentDto);
        return new ResponseEntity<>(createdDepartment, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartmentDtoGet> updateDepartment(@PathVariable long id, @RequestBody DepartmentDto departmentDto){

        DepartmentDtoGet updatedDepartment = departmentService.updateDepartment(departmentDto,id);
        return ResponseEntity.ok(updatedDepartment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Department> deleteDepartment(@PathVariable long id){

        departmentService.deleteDepartment(id);
        return ResponseEntity.noContent().build();
    }
}
