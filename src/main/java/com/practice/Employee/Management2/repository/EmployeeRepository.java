package com.practice.Employee.Management2.repository;

import com.practice.Employee.Management2.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {

     List<Employee> getEmployeesByDepartmentDepartmentName(String departmentName);

}
