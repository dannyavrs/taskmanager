package com.example.taskmanager.entity;

import com.example.taskmanager.enums.Priority;
import com.example.taskmanager.enums.Status;
import com.example.taskmanager.enums.TypeofTask;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
// generator is used to avoid infinite loop that causes stackoverflow error
// property is the field that is used to generate the id
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
@Table(name = "taskkkkkk")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;
    @Temporal(TemporalType.DATE)
    @Column(name = "startdate")
    private Date startdate;

    @Temporal(TemporalType.DATE)
    @Column(name = "enddate")
    private Date enddate;
@Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "expectedemployees")
    private String expectedemployees;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    private Priority priority;
    @Enumerated(EnumType.STRING)
    @Column(name = "typeoftask")
    private TypeofTask typeoftask;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name="TaskAssignment",
            joinColumns = @JoinColumn(name="task_id"),
            inverseJoinColumns = @JoinColumn(name="employee_id"))
    //@JsonManagedReference // to avoid infinite loop that causes stackoverflow error for many to many relationship
    private List<Employee> employees;

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public TypeofTask getTypeoftask() {
        return typeoftask;
    }

    public void setTypeoftask(TypeofTask typeoftask) {
        this.typeoftask = typeoftask;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    // getters and setters
    // ...
public Task() {
    }
    public Task(String description, Date startdate, Date enddate, Status status, String expectedemployees) {
        this.description = description;
        this.startdate = startdate;
        this.enddate = enddate;
        this.status = status;
        this.expectedemployees = expectedemployees;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startDate) {
        this.startdate = startDate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date endDate) {
        this.enddate = endDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getExpectedemployees() {
        return expectedemployees;
    }

    public void setExpectedemployees(String expectedEmployees) {
        this.expectedemployees = expectedEmployees;
    }

    //add a convenience method
    public void addEmployee(Employee employee) {
        if (employees == null) {
            employees = new ArrayList<>();
        }
        employees.add(employee);
    }


    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", startdate=" + startdate +
                ", enddate=" + enddate +
                ", status=" + status +
                ", expectedemployees='" + expectedemployees + '\'' +
                ", priority=" + priority +
                ", typeoftask=" + typeoftask +
                '}';
    }

    public void removeEmployee(Employee employee) {
        if (employees == null) {
            employees = new ArrayList<>();
        }
        employees.remove(employee);
    }
}
