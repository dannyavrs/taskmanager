package com.example.taskmanager.restcontroller;

import com.example.taskmanager.dao.TaskDAO;

import com.example.taskmanager.entity.Task;
import com.example.taskmanager.exceptions.EmployeeNotFoundException;
import com.example.taskmanager.exceptions.TaskNotFoundException;
import com.example.taskmanager.exceptions.TeamNotFoundException;
import com.example.taskmanager.exceptions.TeamRequirementsException;
import com.example.taskmanager.service.TaskService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;


import org.springframework.web.bind.annotation.*;

@RestController

@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskDAO taskDao;

    @Autowired
    private TaskService taskService;

    @GetMapping
    public List<Task> getAllTasks() {
        return taskDao.getAllTasks();
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Long id) {
        return taskDao.getTaskById(id);
    }

    @PostMapping
    @Transactional
        public Task createTask(@RequestBody Task task) {
            taskDao.createTask(task);
            return task;

        }




    @PutMapping
    @Transactional
    public void updateTask(@RequestBody Task task) {
        taskDao.updateTask(task);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void deleteTask(@PathVariable Long id) {
        taskDao.deleteTask(id);
    }

    @PostMapping("/addEmployeeToTask") // http path: /tasks/addEmployeeToTask?taskId=1&employeeId=1
    @Transactional
    public ResponseEntity<String> addEmployeeToTask(@RequestParam int taskId, @RequestParam int employeeId) throws TaskNotFoundException, EmployeeNotFoundException, TeamRequirementsException {
        return taskService.addEmployeeToTask(taskId, employeeId);
    }
    @DeleteMapping("/removeEmployeeFromTask") // http path: /tasks/removeEmployeeFromTask?taskId=1&employeeId=1
    @Transactional
    public ResponseEntity<String> removeEmployeeFromTask(@RequestParam int taskId, @RequestParam int employeeId) throws TaskNotFoundException, EmployeeNotFoundException {
        return taskService.removeEmployeeFromTask(taskId, employeeId);
    }
}

