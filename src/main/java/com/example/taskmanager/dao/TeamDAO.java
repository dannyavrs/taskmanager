package com.example.taskmanager.dao;

import com.example.taskmanager.entity.Employee;
import com.example.taskmanager.entity.Team;

import java.util.List;

public interface TeamDAO {
    void addTeam(Team team);
    void updateTeam(Team team);
    void deleteTeam(int teamId);
    List<Team> getAllTeams();
    Team getTeamById(int teamId);



    List<Employee> getEmployeesByTeam(int teamId);
    void addEmployeeToTeam(int teamId, int employeeId);
    void removeEmployeeFromTeam(int teamId, int employeeId);


}
