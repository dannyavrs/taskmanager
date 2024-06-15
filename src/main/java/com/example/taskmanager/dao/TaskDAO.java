package com.example.taskmanager.dao;

import com.example.taskmanager.entity.Task;

import java.util.List;

public interface TaskDAO {
    List<Task> getAllTasks();
    Task getTaskById(Long id);
    void createTask(Task task);
    void updateTask(Task task);
    void deleteTask(Long id);
    void addEmployeeToTask(int taskId, int employeeId);
}
