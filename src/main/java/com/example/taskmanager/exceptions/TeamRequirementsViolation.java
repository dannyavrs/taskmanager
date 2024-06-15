package com.example.taskmanager.exceptions;

public class TeamRequirementsViolation  {
    private String message;
    private String requirement;

    public TeamRequirementsViolation(String requirement, String message) {
        this.message = message;
        this.requirement = requirement;
    }

    public String getMessage() {
        return message;
    }

    public String getRequirement() {
        return requirement;
    }

    @Override
    public String toString() {
        return "TeamRequirementsViolation{" +
                "message='" + message + '\'' +
                '}';
    }
}
