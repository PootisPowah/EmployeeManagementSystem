package com.practice.Employee.Management2.dto;

import java.util.List;

public record EmployeePaginationResponse (
        List<EmployeeDto> content,
        int pageNo,
        int pageSize,
        long totalElements,
        int totalPages,
        boolean last
        ){
}
