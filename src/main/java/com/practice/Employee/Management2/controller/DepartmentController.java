package com.practice.Employee.Management2.controller;

import com.practice.Employee.Management2.dto.DepartmentDto;
import com.practice.Employee.Management2.dto.DepartmentDtoGet;
import com.practice.Employee.Management2.dtoClass.DepartmentDtoGetClass;
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
    public ResponseEntity<DepartmentDtoGetClass> getDepartmentById(@PathVariable int id) {
        DepartmentDtoGetClass department = departmentService.getDepartmentById(id);
       if (department == null) {
           return ResponseEntity.notFound().build();
       }
       return ResponseEntity.ok(department);
    }

    @GetMapping
    public ResponseEntity<List<DepartmentDtoGetClass>> getAllDepartments(){
        List<DepartmentDtoGetClass> department = departmentService.getAllDepartments();

        if(department == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(department);
    }

    @PostMapping
    public ResponseEntity<DepartmentDtoGetClass> createDepartment(@RequestBody DepartmentDto departmentDto){
        DepartmentDtoGetClass createdDepartment = departmentService.addDepartment(departmentDto);
        return new ResponseEntity<>(createdDepartment, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartmentDtoGetClass> updateDepartment(@PathVariable long id, @RequestBody DepartmentDto departmentDto){

        DepartmentDtoGetClass updatedDepartment = departmentService.updateDepartment(departmentDto,id);
        return ResponseEntity.ok(updatedDepartment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DepartmentDtoGetClass> deleteDepartment(@PathVariable long id){
        DepartmentDtoGetClass department = departmentService.getDepartmentById(id);
        if(department == null){
            return ResponseEntity.notFound().build();
        }
        departmentService.deleteDepartment(id);
        return ResponseEntity.noContent().build();
    }
}
