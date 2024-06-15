package com.example.taskmanager.daoimpl;

import com.example.taskmanager.dao.DepartmentDAO;
import com.example.taskmanager.entity.Department;
import com.example.taskmanager.entity.Team;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DepartmentDAOImpl implements DepartmentDAO {
    @Autowired
    private EntityManager entityManager;

    @Override
    @Transactional
    public void addDepartment(Department department) {
        entityManager.persist(department);
    }

    @Override
    @Transactional
    public void updateDepartment(Department department) {
        entityManager.merge(department);
    }

    @Override
    @Transactional
    public void deleteDepartment(int departmentId) {
        Department department = getDepartmentById(departmentId);
        if (department != null) {
            entityManager.remove(department);
        }
    }


    @Override
    public List<Department> getAllDepartments() {
        return entityManager.createQuery("from Department", Department.class).getResultList();
    }

    @Override
    public Department getDepartmentById(int departmentId) {
        return entityManager.find(Department.class, departmentId);
    }
    @Override
    @Transactional
    public void addTeamToDepartment(int departmentId, int teamId) {
        Department department = entityManager.find(Department.class, departmentId);
        Team team = entityManager.find(Team.class, teamId);
        if (department != null && team != null) {
            department.addTeam(team);
            entityManager.merge(department);
        }
    }

    @Override
    @Transactional
    public void removeTeamFromDepartment(int departmentId, int teamId) {
        Department department = entityManager.find(Department.class, departmentId);
        Team team = entityManager.find(Team.class, teamId);
        if (department != null && team != null) {
            department.removeTeam(team);
            entityManager.merge(department);
        }
    }
}
