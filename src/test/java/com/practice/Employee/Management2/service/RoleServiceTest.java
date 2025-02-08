package com.practice.Employee.Management2.service;

import com.practice.Employee.Management2.entity.Role;
import com.practice.Employee.Management2.exception.ResourceNotFoundException;
import com.practice.Employee.Management2.repository.RoleRepository;
import com.practice.Employee.Management2.service.impl.RoleServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @InjectMocks
    RoleServiceImpl roleService;

    @Mock
    RoleRepository roleRepository;
    public static Role role;
    @BeforeAll
    static void init(){
         role = new Role(1L, "Janitor");
    }

    @Test
    void shouldGetRoleByIdSuccessfully() {
        //given
        long id = 1;
        //when
        when(roleRepository.findById(id)).thenReturn(Optional.of(role));
        Optional<Role> roleById = Optional.ofNullable(roleService.getRoleById(id));
        //then
        assertTrue(roleById.isPresent());
        verify(roleRepository,times(1)).findById(id);
    }

    @Test
    void shouldNotGetRoleByIdSuccessfully() {
        //given
        long id = 1;
        //when
        when(roleRepository.findById(id)).thenReturn(Optional.empty());

        //then
        assertThrows(ResourceNotFoundException.class, () -> roleService.getRoleById(id));
    }

    @Test
    void shouldGetAllRolesSuccessfully() {
        //given

        //when
        when(roleRepository.findAll()).thenReturn(List.of(role));
        List<Role> allRoles = roleService.getAllRoles();
        //then
        assertNotNull(allRoles);
    }

    @Test
    void shouldNotGetAllRolesSuccessfully() {
        //given

        //when
        when(roleRepository.findAll()).thenReturn(List.of());
        List<Role> allRoles = roleService.getAllRoles();
        //then
        assertThat(allRoles.isEmpty());
    }

    @Test
    void shouldAddRoleSuccessfully() {
        //given
        Role createdRole = new Role(2L, "Truck Driver");
        //when
        when(roleRepository.save(createdRole)).thenReturn(createdRole);
        roleService.addRole(createdRole);
        //then
        verify(roleRepository,times(1)).save(createdRole);
    }

    @Test
    void shouldNotAddRoleSuccessfully() {
        //given
        //when
        when(roleRepository.save(role)).thenThrow(new RuntimeException("Unable to save role"));
        //then
        assertThrows(RuntimeException.class, () -> roleService.addRole(role));
        verify(roleRepository,times(1)).save(role);
    }

    @Test
    void shouldUpdateRoleSuccessfully() {
        //given
      long id = 2;
      String roleName = "Truck Driver";
      role.setId(id);
      role.setRoleName(roleName);
        //when
        when(roleRepository.save(role)).thenReturn(role);
        Role updatedRole = roleService.updateRole(role);
        //then
        assertNotNull(updatedRole);
        assertEquals(roleName, updatedRole.getRoleName());
        assertEquals(id, updatedRole.getId());
        verify(roleRepository,times(1)).save(role);
    }

    @Test
    void shouldNotUpdateRoleSuccessfully() {
        //given
        long id = 2;
        String roleName = "Truck Driver";
        role.setId(id);
        role.setRoleName(roleName);
        //when
        when(roleRepository.save(role)).thenThrow(new RuntimeException("Unable to save role"));
        //then
        assertThrows(RuntimeException.class, () -> roleService.updateRole(role));
        verify(roleRepository,times(1)).save(role);
    }

    @Test
    void shouldDeleteRoleSuccessfully() {

        //given
        //when
        doNothing().when(roleRepository).deleteById(role.getId());
        roleService.deleteRole(role.getId());
        //then
        verify(roleRepository,times(1)).deleteById(role.getId());
    }
}