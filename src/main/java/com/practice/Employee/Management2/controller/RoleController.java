package com.practice.Employee.Management2.controller;

import com.practice.Employee.Management2.entity.Role;
import com.practice.Employee.Management2.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable int id) {
       Role role = roleService.getRoleById(id);
       if (role == null) {
           return ResponseEntity.notFound().build();
       }
       return ResponseEntity.ok(role);
    }

    @GetMapping
    public ResponseEntity<List<Role>> getAllRoles(){
        List<Role> roles = roleService.getAllRoles();

        if(roles == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(roles);
    }

    @PostMapping
    public ResponseEntity<Role> createRole(@RequestBody Role role){
        Role createdRole = roleService.addRole(role);
        return new ResponseEntity<>(createdRole, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Role> updateRole(@PathVariable long id, @RequestBody Role role){
        Role existingRole = roleService.getRoleById(id);
        if(existingRole == null){
            return ResponseEntity.notFound().build();
        }
        role.setId(id);
        Role updatedRole = roleService.updateRole(role);
        return ResponseEntity.ok(updatedRole);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Role> deleteRole(@PathVariable long id){
        Role role = roleService.getRoleById(id);
        if(role == null){
            return ResponseEntity.notFound().build();
        }
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }
}
