package com.example.taskmanager;

import com.example.taskmanager.dao.DepartmentDAO;
import com.example.taskmanager.dao.EmployeeDAO;
import com.example.taskmanager.dao.TaskDAO;
import com.example.taskmanager.dao.TeamDAO;
import com.example.taskmanager.exceptions.EmployeeNotFoundException;
import com.example.taskmanager.exceptions.TeamNotFoundException;
import com.example.taskmanager.exceptions.TeamRequirementsException;
import com.example.taskmanager.service.TeamService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication

public class TaskmanagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskmanagerApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(EmployeeDAO employeeDAO, DepartmentDAO departmentDAO, TaskDAO taskDAO, TeamDAO teamDAO, TeamService teamService) {
		return runner -> {
			//addEmployeeToTeam(teamService);
			//getallemployeesInTeam(teamDAO);
			//printEmployeeGetTeam(employeeDAO);
		};
	}

	private void printEmployeeGetTeam(EmployeeDAO employeeDAO) {
		System.out.println(employeeDAO.getEmployeeById(2).getTeam());
	}

	private void getallemployeesInTeam(TeamDAO teamDAO) {
		teamDAO.getEmployeesByTeam(1).forEach(System.out::println);
	}

	private void addEmployeeToTeam(TeamService teamService) throws TeamRequirementsException, TeamNotFoundException, EmployeeNotFoundException {
		teamService.addEmployeeToTeam(1, 1);
		//add this to the controller
		//make sure that the user can be able to watch the exception message
	}



}
