package com.example.taskmanager.restcontroller;

import com.example.taskmanager.dao.EmployeeDAO;
import com.example.taskmanager.dao.TeamDAO;
import com.example.taskmanager.entity.Task;
import com.example.taskmanager.entity.Team;
import com.example.taskmanager.exceptions.EmployeeNotFoundException;
import com.example.taskmanager.exceptions.TeamNotFoundException;
import com.example.taskmanager.exceptions.TeamRequirementsException;
import com.example.taskmanager.service.TeamService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teams")
public class TeamController {
    @Autowired
    private TeamDAO teamDAO;
    private EmployeeDAO employeeDAO;

    @Autowired
    private TeamService teamService;

    @GetMapping
    public List<Team> getAllTeams() {
        return teamDAO.getAllTeams();
    }

    @GetMapping("/{id}")
    public Team getTeamById(@PathVariable int id) {
        teamDAO.getTeamById(id);
        return teamDAO.getTeamById(id);
    }

    @PostMapping
    @Transactional
    public Team createTeam(@RequestBody Team team) {
        teamDAO.addTeam(team);
        return team;

    }

    @PutMapping
    @Transactional
    public ResponseEntity<String> updateTeam(@RequestBody Team team) {

        teamDAO.updateTeam(team);
        return ResponseEntity.ok("Team with id " + team.getId() + " updated. if you don't see the team in the department it means you didn't met the requirements");

    }

    @DeleteMapping("/{id}") //path: /teams/1
    @Transactional
    public void deleteTeam(@PathVariable int id) {
        teamDAO.deleteTeam(id);
    }

    @PostMapping("/addEmployeeToTeam") //?teamId=1&employeeId=1
    @Transactional
    public ResponseEntity<String> addEmployeeToTeam(@RequestParam int teamId, @RequestParam int employeeId) throws TeamNotFoundException, EmployeeNotFoundException, TeamRequirementsException{
        //you need to throw the exceptions because you are using the try catch block in the service
        return teamService.addEmployeeToTeam(teamId, employeeId);


    }
    @DeleteMapping("/removeEmployeeFromTeam") //?teamId=1&employeeId=1
    @Transactional
    public ResponseEntity<String>removeEmployeeFromTeam(@RequestParam int teamId, @RequestParam int employeeId) throws TeamNotFoundException, EmployeeNotFoundException {
        return teamService.removeEmployeeFromTeam(teamId, employeeId);

    }

}
