package com.practice.Employee.Management2.dtoClass;

import com.practice.Employee.Management2.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDtoClass {
        private long id;
    private  String firstName;
    private  String lastName;
    private  String email;
    private  String gender;
    private  String phone;
    private Double salary;
    private DepartmentDtoGet department;
    private Role role;
}
