package com.example.taskmanager.restcontroller;

import com.example.taskmanager.dao.DepartmentDAO;
import com.example.taskmanager.dao.EmployeeDAO;
import com.example.taskmanager.entity.Department;
import com.example.taskmanager.entity.Team;
import com.example.taskmanager.exceptions.DepartmentNotFoundException;
import com.example.taskmanager.exceptions.EmployeeNotFoundException;
import com.example.taskmanager.exceptions.TeamNotFoundException;
import com.example.taskmanager.service.DepartmentService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments")
public class DepartmentController {
    @Autowired
    private DepartmentDAO departmentDAO;
    private EmployeeDAO employeeDAO;
    @Autowired
    private DepartmentService departmentService;


    @GetMapping
    public List<Department> getAllDepartments() {
        return departmentDAO.getAllDepartments();
    }

    @GetMapping("/{id}")
    public Department getDepartmentById(@PathVariable int id) {
        return departmentDAO.getDepartmentById(id);
    }

        @PostMapping
        @Transactional
        public Department createDepartment (@RequestBody Department department){
            departmentDAO.addDepartment(department);
            return department;

        }

        @PutMapping
        @Transactional
        public void updateDepartment (@RequestBody Department team){

        departmentDAO.updateDepartment(team);

        }

        @DeleteMapping("/{id}")
        @Transactional
        public void deleteDepartment (@PathVariable int id){
            departmentDAO.deleteDepartment(id);
        }

    @PostMapping("/addTeamToDepartment") //?departmentId=1&teamId=1
    @Transactional
    public ResponseEntity<String> addTeamToDepartment (@RequestParam int departmentId, @RequestParam int teamId) throws DepartmentNotFoundException, TeamNotFoundException {
        return departmentService.addTeamToDepartment(departmentId, teamId);
    }
    @DeleteMapping("/removeTeamFromDepartment") //?departmentId=1&teamId=1 similar to the request param!
    @Transactional
    public ResponseEntity<String>removeTeamFromDepartment(@RequestParam int departmentId, @RequestParam int teamId) throws TeamNotFoundException, DepartmentNotFoundException {
        return departmentService.removeTeamFromDepartment(departmentId, teamId);

    }
    }


