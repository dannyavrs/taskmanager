package com.example.taskmanager.service;

import com.example.taskmanager.daoimpl.EmployeeDAOImpl;
import com.example.taskmanager.daoimpl.TaskDAOImpl;
import com.example.taskmanager.exceptions.EmployeeNotFoundException;
import com.example.taskmanager.exceptions.TaskNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    private final EmployeeDAOImpl employeeDAO;
    private final TaskDAOImpl taskDAO;

    @Autowired //constructor injection keyword to inject the dependencies
    public EmployeeService(EmployeeDAOImpl employeeDAO, TaskDAOImpl taskDAO) {
        this.employeeDAO = employeeDAO;
        this.taskDAO = taskDAO;
    }

    //method to add task to employee
    public ResponseEntity<String> addTaskToEmployee(int taskId, int employeeId) throws TaskNotFoundException, EmployeeNotFoundException {
        try {
            if (taskDAO.getTaskById((long) taskId) == null) {
                throw new TaskNotFoundException("Task with id " + taskId + " not found");

            }
            if (employeeDAO.getEmployeeById(employeeId) == null) {
                throw new EmployeeNotFoundException("Employee with id " + employeeId + " not found");
            }
        } catch (EmployeeNotFoundException | TaskNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        employeeDAO.addTaskToEmployee(taskId, employeeId);
        return ResponseEntity.ok("Task with id " + taskId + " added to employee with id " + employeeId);
    }
}
