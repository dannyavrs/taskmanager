package com.example.taskmanager.service;

import com.example.taskmanager.dao.EmployeeDAO;
import com.example.taskmanager.daoimpl.EmployeeDAOImpl;
import com.example.taskmanager.daoimpl.TeamDAOImpl;
import com.example.taskmanager.entity.Employee;
import com.example.taskmanager.entity.Team;
import com.example.taskmanager.enums.Role;
import com.example.taskmanager.exceptions.EmployeeNotFoundException;
import com.example.taskmanager.exceptions.TeamNotFoundException;
import com.example.taskmanager.exceptions.TeamRequirementsException;
import com.example.taskmanager.exceptions.TeamRequirementsViolation;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeamService {

    private final TeamDAOImpl teamDAO;
    private final EmployeeDAOImpl employeeDAO;
    //injecting the dependencies
    @Autowired //constructor injection keyword to inject the dependencies
    public TeamService(TeamDAOImpl teamDAO, EmployeeDAOImpl employeeDAO) {
        this.teamDAO = teamDAO;
        this.employeeDAO = employeeDAO;
    }
    //method to add employee to team
    @Transactional
    public  ResponseEntity<String> addEmployeeToTeam(int teamId, int employeeId) throws TeamRequirementsException, EmployeeNotFoundException, TeamNotFoundException {
        Team team = teamDAO.getTeamById(teamId);

        try {
            if (team == null) {
                throw new TeamNotFoundException("Team with id " + teamId + " not found");
            }
            Employee employee = employeeDAO.getEmployeeById(employeeId);
            if (employee == null) {
                throw new EmployeeNotFoundException("Employee with id " + employeeId + " not found");
            }
            if (team.getEmployees().contains(employee)) {
                throw new RuntimeException("Employee with id " + employeeId + " already in team with id " + teamId);
            }
            List<TeamRequirementsViolation> requirementsViolations = checkTeamRequirements(team, employee);
            if (!requirementsViolations.isEmpty()) {
                throw new TeamRequirementsException("Team with id " + teamId + " has not meet the requirements" + " " + requirementsViolations.toString());
            }
        } catch (TeamNotFoundException | EmployeeNotFoundException | TeamRequirementsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        teamDAO.addEmployeeToTeam(teamId, employeeId);
        return ResponseEntity.ok("Employee with id " + employeeId + " added to team with id " + teamId);



    }

    private List<TeamRequirementsViolation> checkTeamRequirements(Team team, Employee employeeToAdd) {
        int teamManagerCount = 0;
        int developerCount = 0;
        int testerCount = 0;
        int salesCount = 0;

        for (Employee employee : team.getEmployees()) {
            switch (employee.getRole()) {
                case TeamManager:
                    teamManagerCount++;
                    break;
                case Developer:
                    developerCount++;
                    break;
                case Tester:
                    testerCount++;
                    break;
                case Sales:
                    salesCount++;
                    break;
                default:
                    break;
            }
        }
        List<TeamRequirementsViolation> requirementViolations = new ArrayList<>();
        if (employeeToAdd.getRole() == Role.TeamManager && teamManagerCount >= 1) {
            requirementViolations.add(new TeamRequirementsViolation("TeamManager", "Team already has a team manager"));
        }
        if (employeeToAdd.getRole() == Role.Developer && developerCount >= 2) {
            requirementViolations.add(new TeamRequirementsViolation("Developer", "Team already has 2 developers"));
        }
        if (employeeToAdd.getRole() == Role.Tester && testerCount >= 1) {
            requirementViolations.add(new TeamRequirementsViolation("Tester", "Team already has a tester"));
        }
        if (employeeToAdd.getRole() == Role.Sales && salesCount >= 1) {
            requirementViolations.add(new TeamRequirementsViolation("Sales", "Team already has a salesperson"));
        }

        // Add more requirements here...

        return requirementViolations;



    }
    public ResponseEntity<String> removeEmployeeFromTeam(int teamId, int employeeId) throws TeamNotFoundException, EmployeeNotFoundException{
        Team team = teamDAO.getTeamById(teamId);
        try {
            if (team == null) {
                throw new TeamNotFoundException("Team with id " + teamId + " not found");
            }
            Employee employee = employeeDAO.getEmployeeById(employeeId);
            if (employee == null) {
                throw new EmployeeNotFoundException("Employee with id " + employeeId + " not found");
            }
        } catch (TeamNotFoundException | EmployeeNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        teamDAO.removeEmployeeFromTeam(teamId, employeeId);
        return ResponseEntity.ok("Employee with id " + employeeId + " removed from team with id " + teamId);


    }
    public ResponseEntity<String> AlertEmployeePassRequirements(Employee employee, Team team){
        List<TeamRequirementsViolation> requirementsViolations = checkTeamRequirements(team, employee);
        if (!requirementsViolations.isEmpty()) {
            return ResponseEntity.badRequest().body("Employee with id " + employee.getId() + " does not meet the requirements" + " " + requirementsViolations.toString());
        }
        return ResponseEntity.ok("Employee with id " + employee.getId() + " meets the requirements");

    }


}
