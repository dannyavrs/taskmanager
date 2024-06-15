package com.example.taskmanager.daoimpl;

import com.example.taskmanager.dao.EmployeeDAO;
import com.example.taskmanager.entity.Employee;
import com.example.taskmanager.entity.Task;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class EmployeeDAOImpl implements EmployeeDAO {
    @Autowired
    private EntityManager entityManager;


    @Override
    @Transactional
    public void addEmployee(Employee employee) {
        entityManager.persist(employee);
        System.out.println(employee.toString());
    }

    @Override
    public List<Employee> getAllEmployees() {
        return entityManager.createQuery("from Employee", Employee.class).getResultList();
    }

    @Override
    @Transactional
    public void deleteEmployee(int employeeId) {
        if(getEmployeeById(employeeId)!=null){
            entityManager.remove(getEmployeeById(employeeId));
        }
    }

    @Override
    @Transactional
    public Employee updateEmployee(Employee employee) {
        entityManager.merge(employee);
        return employee;
    }



    @Override
    public Employee getEmployeeById(int employeeId) {
        return entityManager.find(Employee.class, employeeId);
    }

    @Override
    @Transactional
    public void addTaskToEmployee(int taskId, int employeeId) {
        Task task = entityManager.find(Task.class, (long) taskId);
        Employee employee = entityManager.find(Employee.class, employeeId);
        if (task != null && employee != null) {
            employee.addTask(task); //add the employee to the task
            entityManager.merge(task); //update the task
            entityManager.merge(employee); //update the employee
        }
    }

    @Override
    @Transactional
    public void removeTaskFromEmployee(int employeeId, int taskId) {
        Task task = entityManager.find(Task.class, (long) taskId);
        Employee employee = entityManager.find(Employee.class, employeeId);
        if (task != null && employee != null) {
            task.removeEmployee(employee); //remove the employee from the task
            entityManager.merge(task); //update the task
            entityManager.merge(employee); //update the employee
        }
    }
}
