package com.example.taskmanager.restcontroller;

import com.example.taskmanager.dao.EmployeeDAO;
import com.example.taskmanager.dao.TaskDAO;
import com.example.taskmanager.entity.Employee;
import com.example.taskmanager.entity.Task;
import com.example.taskmanager.enums.Role;
import com.example.taskmanager.exceptions.EmployeeNotFoundException;
import com.example.taskmanager.exceptions.TaskNotFoundException;
import com.example.taskmanager.service.EmployeeService;
import com.example.taskmanager.service.TeamService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



    @RestController

    @RequestMapping("/employees")
    public class EmployeeController {

        @Autowired
        private EmployeeDAO employeeDAO;

        @Autowired
        private TeamService teamService;

        @Autowired
        private EmployeeService employeeService;

        @GetMapping
        public List<Employee> getAllEmployees() {
            return employeeDAO.getAllEmployees();
        }

        @GetMapping("/{id}")
        public Employee getEmployeeById(@PathVariable int id) {
            return employeeDAO.getEmployeeById(id);
        }

        @PostMapping
        public Employee createEmployee(@RequestBody Employee employee) {
            employeeDAO.addEmployee(employee);
            return employee;

        }


        @PutMapping //?id=1
        public ResponseEntity<String> updateEmployee(@RequestBody Employee employee) {
            /*
            ResponseEntity<String> responseEntity = null;
            if(employee.getId() == 0){
                throw new RuntimeException("Id is required");
            }
                if (employeeDAO.getEmployeeById(employee.getId()) == null) {
                    responseEntity = ResponseEntity.badRequest().body("Employee with id " + employee.getId() + " not found");
                }

                else {
                    employeeDAO.updateEmployee(employee);
                    responseEntity = ResponseEntity.ok("Employee with id " + employee.getId() + " updated");
                }

            return responseEntity;
             */
            //employee.getTeam==null
           //return teamService.updateEmployeeById(employee.getId());
            if(employee.getTeam()==null) {
                employeeDAO.updateEmployee(employee);
                return ResponseEntity.ok("Employee with id " + employee.getId() + " updated. if you don't see him in the team it means you didn't met the requirements");
            }
            else{
                return teamService.AlertEmployeePassRequirements(employee, employee.getTeam());
            }
        }

        @DeleteMapping("/{id}")
        public void deleteEmployee(@PathVariable int id) {
            employeeDAO.deleteEmployee(id);
        }
        @PostMapping("/addTaskToEmployee") // http path: /tasks/addEmployeeToTask?employeeId=1&taskId=1
        @Transactional
        public ResponseEntity<String> addTaskToEmployee(@RequestParam int employeeId, @RequestParam int taskId) throws TaskNotFoundException, EmployeeNotFoundException {
            return employeeService.addTaskToEmployee(employeeId, taskId);
        }
    }

