package com.example.taskmanager.service;

import com.example.taskmanager.daoimpl.EmployeeDAOImpl;
import com.example.taskmanager.daoimpl.TaskDAOImpl;
import com.example.taskmanager.entity.Employee;
import com.example.taskmanager.entity.Task;
import com.example.taskmanager.enums.Role;
import com.example.taskmanager.enums.TypeofTask;
import com.example.taskmanager.exceptions.EmployeeNotFoundException;
import com.example.taskmanager.exceptions.TaskNotFoundException;
import com.example.taskmanager.exceptions.TeamRequirementsException;
import com.example.taskmanager.exceptions.TeamRequirementsViolation;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {
    private final TaskDAOImpl taskDAO;
    private final EmployeeDAOImpl employeeDAO;

    private final EntityManager entityManager;

    //injecting the dependencies
    @Autowired //constructor injection keyword to inject the dependencies
    public TaskService(TaskDAOImpl taskDAO, EmployeeDAOImpl employeeDAO, EntityManager entityManager) {
        this.taskDAO = taskDAO;
        this.employeeDAO = employeeDAO;
        this.entityManager = entityManager;
    }

    //method to add employee to task
    @Transactional
    public ResponseEntity<String> addEmployeeToTask(int taskId, int employeeId) throws TaskNotFoundException, EmployeeNotFoundException, TeamRequirementsException {
        Task task= taskDAO.getTaskById((long) taskId);
        Employee employee= employeeDAO.getEmployeeById(employeeId);
        try {
            if (taskDAO.getTaskById((long) taskId) == null) {
                throw new TaskNotFoundException("Task with id " + taskId + " not found");

            }
            if (employeeDAO.getEmployeeById(employeeId) == null) {
                throw new EmployeeNotFoundException("Employee with id " + employeeId + " not found");
            }
            taskDAO.addEmployeeToTask(taskId, employeeId); //1
            employeeDAO.addTaskToEmployee(employeeId, taskId); //1
            entityManager.flush();// flush the changes to the database
            List<TeamRequirementsViolation> requirementsViolations = checkTaskRequirements(task, employee);
            if (!requirementsViolations.isEmpty()) {
                taskDAO.removeEmployeeFromTask(taskId, employeeId);
                employeeDAO.removeTaskFromEmployee(employeeId, taskId);
                throw new TeamRequirementsException("Task with id " + taskId + " has not meet the requirements" + " " + requirementsViolations.toString());
            }
        } catch (TeamRequirementsException | EmployeeNotFoundException | TaskNotFoundException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("Employee with id " + employeeId + " added to task with id " + taskId);


    }
    @Transactional
    private List<TeamRequirementsViolation> checkTaskRequirements(Task task, Employee employeeToAdd) {
        int TaskId= task.getId().intValue();
        int employeeId= employeeToAdd.getId();
        int teamManagerCount=0;
        int developerCount = 0;
        int testerCount = 0;
        int salesCount = 0;
        int marketingCount = 0;

        for (Employee employee : task.getEmployees()) {
            switch (employee.getRole()) {
                case TeamManager -> teamManagerCount++;
                case Developer -> developerCount++;
                case Tester -> testerCount++;
                case Sales -> salesCount++;
                case Marketing -> marketingCount++;
                default -> {
                }
            }
        }
        System.out.println("Team Manager Count: " + teamManagerCount);
        List<TeamRequirementsViolation> requirementViolations = new ArrayList<>();
        if(task.getTypeoftask()==TypeofTask.SALES_TASK){ //if the task is a sales task them move to another type of task
            if(employeeToAdd.getRole()==Role.TeamManager || employeeToAdd.getRole()==Role.Sales){
                if(teamManagerCount<=1 && salesCount<=2){
                    ResponseEntity.ok("Employee with id " + employeeId + " added to task with id " + TaskId);
                }
                else{
                    requirementViolations.add(new TeamRequirementsViolation("SalesTask", "Task does not meet the requirements for Sales Task"));
                }
            }
        }
        else if(task.getTypeoftask()==TypeofTask.MARKETING_TASK){
            if(employeeToAdd.getRole()==Role.TeamManager || employeeToAdd.getRole()==Role.Marketing){
                if(teamManagerCount<=1 && marketingCount<=2){
                    ResponseEntity.ok("Employee with id " + employeeId + " added to task with id " + TaskId);
                }
                else{
                    requirementViolations.add(new TeamRequirementsViolation("MarketingTask", "Task does not meet the requirements for Marketing Task"));
                }
            }
        }
        else if(task.getTypeoftask()==TypeofTask.TESTING_TASK){
            if(employeeToAdd.getRole()==Role.TeamManager || employeeToAdd.getRole()==Role.Tester){
                if(teamManagerCount<=1 && testerCount<=2){
                    ResponseEntity.ok("Employee with id " + employeeId + " added to task with id " + TaskId);
                }
                else{
                    requirementViolations.add(new TeamRequirementsViolation("TestingTask", "Task does not meet the requirements for Testing Task"));
                }
            }
        }
        else if(task.getTypeoftask()==TypeofTask.DEVELOPMENT_TASK){
            if(employeeToAdd.getRole()==Role.TeamManager || employeeToAdd.getRole()==Role.Developer){
                if(teamManagerCount<=1 && developerCount<=2){
                    ResponseEntity.ok("Employee with id " + employeeId + " added to task with id " + TaskId);
                }
                else{
                    requirementViolations.add(new TeamRequirementsViolation("DevelopmentTask", "Task does not meet the requirements for Development Task"));
                }
            }
        }
        else{
            requirementViolations.add(new TeamRequirementsViolation("TaskType", "Task type not found"));
        }




        return requirementViolations;

    }
    public ResponseEntity<String> removeEmployeeFromTask(int taskId, int employeeId) throws TaskNotFoundException, EmployeeNotFoundException{
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
        taskDAO.removeEmployeeFromTask(taskId, employeeId);
        return ResponseEntity.ok("Employee with id " + employeeId + " removed from task with id " + taskId);
    }
}
