package com.example.taskmanager.dao;

import com.example.taskmanager.entity.Department;

import java.util.List;

public interface DepartmentDAO {
    void addDepartment(Department department);
    void updateDepartment(Department department);
    void deleteDepartment(int departmentId);
    List<Department> getAllDepartments();
    Department getDepartmentById(int departmentId);
    void addTeamToDepartment(int departmentId, int teamId);

    void removeTeamFromDepartment(int departmentId, int teamId);
}
