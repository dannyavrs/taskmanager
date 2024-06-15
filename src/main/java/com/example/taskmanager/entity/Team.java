package com.example.taskmanager.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "team")
public class Team {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH},mappedBy = "team") //Lazy by default, mappedby is always in the one side
    @JsonManagedReference // to avoid infinite loop
    private List<Employee> employees;

    @ManyToOne()
    @JoinColumn(name = "department_id")// join column is in the many side always, have to be the name of the foreign key column in the database
    @JsonBackReference // to avoid infinite loop that causes stackoverflow error
    private Department department;
    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    // Add a constructor with no arguments because JPA requires it
    public Team() {
    }
    // Add a constructor with all arguments except id
    public Team(String name, String description) {
        this.name = name;
        this.description = description;
    }
    // Add getters and setters for all fields


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    // Add a convenience method to add an employee to the team
    public void addEmployee(Employee employee) {
        if (employees == null) {
            employees = new ArrayList<>();
        }
        employees.add(employee);
        employee.setTeam(this);
    }

// Add a convenience method to remove an employee from the team
    public void removeEmployee(Employee employee) {
        if (employees != null) {
            employees.remove(employee);
            employee.setTeam(null);
        }
        else{
            System.out.println("No employee to remove");
        }
    }
}
