package com.example.taskmanager.entity;

import com.example.taskmanager.enums.Role;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
// generator is used to avoid infinite loop that causes stackoverflow error
// property is the field that is used to generate the id
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id") // to avoid infinite loop that causes stackoverflow error
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "mail")
    private String mail;
    @Enumerated(EnumType.STRING)
    @Column(name="role")
    private Role role;
    @Temporal(TemporalType.DATE)
    @Column(name = "joindate")
    private Date joindate;

    @ManyToOne()
    @JoinColumn(name = "team_id")// join column is in the many side always, have to be the name of the foreign key column in the database
    //@JsonBackReference // to avoid infinite loop that causes stackoverflow error
    private Team team;

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})
   // @JsonBackReference // to avoid infinite loop that causes stackoverflow error for many to many relationship
    //@JsonIgnore // to avoid infinite loop that causes stackoverflow error for many to many relationship
    @JoinTable(name="TaskAssignment",
            joinColumns = @JoinColumn(name="employee_id"),
            inverseJoinColumns = @JoinColumn(name="task_id"))
    private List<Task> tasks;

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public Employee() {
    }

    public Employee(String name, String mail, Role role, Date joindate) {
        this.name = name;
        this.mail = mail;
        this.role = role;
        this.joindate = joindate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Date getJoindate() {
        return joindate;
    }

    public void setJoindate(Date joindate) {
        this.joindate = joindate;
    }

    //add a convenience method for bi-directional relationship
    public void addTask(Task task) {
        if (tasks == null) {
            tasks = new ArrayList<>();
        }
        tasks.add(task);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", mail='" + mail + '\'' +
                ", role='" + role + '\'' +
                ", joindate=" + joindate +
                '}';
    }

    public void removeTask(Task task) {
        if (tasks == null) {
            tasks = new ArrayList<>();
        }
        tasks.remove(task);
    }
}
