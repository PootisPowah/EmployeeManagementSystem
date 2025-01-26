package com.practice.Employee.Management2.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "departments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="department_id")
    private Long id;

    @NotNull
    @Column(name="department_name", unique=true)
    private String departmentName;

    @OneToOne()
    @JoinColumn(name = "manager_id")
    //@JsonBackReference
    private Employee manager;
}
