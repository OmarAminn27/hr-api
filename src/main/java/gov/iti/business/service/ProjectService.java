package gov.iti.business.service;

import gov.iti.business.entities.Employee;
import gov.iti.business.entities.Project;
import gov.iti.business.util.EntityManagerCreator;
import gov.iti.persistence.EmployeeDAO;
import gov.iti.persistence.ProjectDAO;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ProjectService {
    private final ProjectDAO projectDAO = ProjectDAO.getInstance();

    public Project getProject (Integer projectID) {
        EntityManager entityManager = EntityManagerCreator.generateEntityManager();
        return projectDAO.findOneById(projectID, entityManager).orElse(null);
    }
    public Set<Project> getProjectsByIDs (EntityManager entityManager, Set<Integer> IDs) {
        return IDs.stream().map(this::getProject).collect(Collectors.toSet());
    }
}
