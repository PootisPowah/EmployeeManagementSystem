package com.practice.Employee.Management2.service;

import com.practice.Employee.Management2.entity.Role;

import java.util.List;

public interface RoleService {
    Role getRoleById(long id);
    List<Role> getAllRoles();
    Role addRole(Role role);
    Role updateRole(Role role);
    void deleteRole(long id);
}
