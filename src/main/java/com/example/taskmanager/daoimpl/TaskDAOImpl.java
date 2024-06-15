package com.example.taskmanager.daoimpl;

import com.example.taskmanager.dao.TaskDAO;
import com.example.taskmanager.entity.Employee;
import com.example.taskmanager.entity.Task;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
public class TaskDAOImpl implements TaskDAO {
    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Task> getAllTasks() {
        return entityManager.createQuery("from Task", Task.class).getResultList();
    }

    @Override
    public Task getTaskById(Long id) {
        return entityManager.find(Task.class, id);
    }

    @Override
    @Transactional
    public void createTask(Task task) {
        entityManager.persist(task);
        System.out.println(task.toString());
    }

    @Override
    @Transactional
    public void updateTask(Task task) {
        entityManager.merge(task);
    }

    @Override
    @Transactional
    public void deleteTask(Long id) {
        Task task = getTaskById(id);
        if (task != null) {
            entityManager.remove(task);
        }
    }

    @Override
    @Transactional
    public void addEmployeeToTask(int taskId, int employeeId) {
        Task task = getTaskById((long) taskId);
        Employee employee = entityManager.find(Employee.class, employeeId);
        if (task != null && employee != null) {
            employee.addTask(task); //add the task to the employee
            entityManager.merge(task); //update the task
            entityManager.merge(employee); //update the employee
        }
    }
    public void removeEmployeeFromTask(int taskId, int employeeId) {
        Task task = getTaskById((long) taskId);
        Employee employee = entityManager.find(Employee.class, employeeId);
        if (task != null && employee != null) {
            employee.removeTask(task); // remove the employee from the task
            entityManager.merge(task); //update the task
            entityManager.merge(employee); //update the employee
        }
    }
}
