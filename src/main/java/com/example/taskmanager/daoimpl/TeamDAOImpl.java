package com.example.taskmanager.daoimpl;

import com.example.taskmanager.dao.TeamDAO;
import com.example.taskmanager.entity.Department;
import com.example.taskmanager.entity.Employee;
import com.example.taskmanager.entity.Team;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TeamDAOImpl implements TeamDAO {
    @Autowired
    private EntityManager entityManager;
    @Override
    public void addTeam(Team team) {
        entityManager.persist(team);
    }

    @Override
    public void updateTeam(Team team) {
        entityManager.merge(team);
    }

    @Override
    public void deleteTeam(int teamId) {
        Team team = getTeamById(teamId);
        if (team != null) {
            entityManager.remove(team);
        }
    }

    @Override
    public List<Team> getAllTeams() {
        return entityManager.createQuery("from Team", Team.class).getResultList();
    }

    @Override
    public Team getTeamById(int teamId) {
        return entityManager.find(Team.class, teamId);
    }

    @Override
    public List<Employee> getEmployeesByTeam(int teamId) {
            return entityManager.createQuery(
                            "SELECT e FROM Employee e JOIN FETCH e.team t WHERE t.id = :teamId",
                            Employee.class)
                    .setParameter("teamId", teamId)
                    .getResultList();

    }

    @Override
    public void addEmployeeToTeam(int teamId, int employeeId) {
        Team team = getTeamById(teamId);
        Employee employee = entityManager.find(Employee.class, employeeId);
        if (team != null && employee != null) {
            team.addEmployee(employee);
            entityManager.merge(team);
        }
    }

    @Override
    public void removeEmployeeFromTeam(int teamId, int employeeId) {
        Team team = getTeamById(teamId);
        Employee employee = entityManager.find(Employee.class, employeeId);
        if (team != null && employee != null) {
            team.removeEmployee(employee);
            entityManager.merge(team);
        }

    }


}
