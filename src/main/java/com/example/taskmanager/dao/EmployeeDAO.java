package com.example.taskmanager.dao;

import com.example.taskmanager.entity.Employee;

import java.util.List;

public interface EmployeeDAO{
     void addEmployee(Employee employee);
     List<Employee> getAllEmployees();
     void deleteEmployee(int employeeId);
     Employee updateEmployee(Employee employee);
     Employee getEmployeeById(int employeeId);

     void addTaskToEmployee(int taskId, int employeeId);


     void removeTaskFromEmployee(int employeeId, int taskId);
}
