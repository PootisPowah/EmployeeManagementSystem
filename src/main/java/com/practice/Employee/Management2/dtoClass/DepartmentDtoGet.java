package com.practice.Employee.Management2.dtoClass;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDtoGet{
        private long id;
        private String departmentName;
        private Long managerId;

        
}
