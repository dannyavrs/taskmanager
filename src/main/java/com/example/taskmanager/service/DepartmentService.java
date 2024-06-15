package com.example.taskmanager.service;

import com.example.taskmanager.daoimpl.DepartmentDAOImpl;
import com.example.taskmanager.daoimpl.EmployeeDAOImpl;
import com.example.taskmanager.daoimpl.TeamDAOImpl;
import com.example.taskmanager.entity.Department;
import com.example.taskmanager.entity.Team;
import com.example.taskmanager.exceptions.DepartmentNotFoundException;
import com.example.taskmanager.exceptions.EmployeeNotFoundException;
import com.example.taskmanager.exceptions.TeamNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class DepartmentService {
    private final TeamDAOImpl teamDAO;
    private final EmployeeDAOImpl employeeDAO;

    private final DepartmentDAOImpl departmentDAO;
    //injecting the dependencies
    @Autowired
    public DepartmentService(TeamDAOImpl teamDAO, EmployeeDAOImpl employeeDAO, DepartmentDAOImpl departmentDAO) {
        this.teamDAO = teamDAO;
        this.employeeDAO = employeeDAO;
        this.departmentDAO = departmentDAO;
    }
    //method to add team to department
    @Transactional //to make the method transactional
    public ResponseEntity<String> addTeamToDepartment(int departmentId, int teamId) throws DepartmentNotFoundException,TeamNotFoundException{
        Department department = departmentDAO.getDepartmentById(departmentId);
        Team team = teamDAO.getTeamById(teamId);
        try{
            if (department == null) {
                throw new DepartmentNotFoundException("Department with id " + departmentId + " not found");
            }
            if (team == null) {
                throw new TeamNotFoundException("Team with id " + teamId + " not found");
            }
        }catch (DepartmentNotFoundException | TeamNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }


        departmentDAO.addTeamToDepartment(departmentId, teamId);
        return ResponseEntity.ok("Team with id " + teamId + " added to department with id " + departmentId);
    }

    public ResponseEntity<String> removeTeamFromDepartment(int departmentId, int teamId) throws DepartmentNotFoundException, TeamNotFoundException {
        Department department = departmentDAO.getDepartmentById(departmentId);
        Team team = teamDAO.getTeamById(teamId);
        try{
            if (department == null) {
                throw new DepartmentNotFoundException("Department with id " + departmentId + " not found");
            }
            if (team == null) {
                throw new TeamNotFoundException("Team with id " + teamId + " not found");
            }
        }catch (DepartmentNotFoundException | TeamNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        departmentDAO.removeTeamFromDepartment(departmentId, teamId);
        return ResponseEntity.ok("Team with id " + teamId + " removed from department with id " + departmentId);
    }
}
