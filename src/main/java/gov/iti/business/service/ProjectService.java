package gov.iti.business.service;

import gov.iti.business.entities.Department;
import gov.iti.business.entities.Employee;
import gov.iti.business.entities.Project;
import gov.iti.business.util.EntityManagerCreator;
import gov.iti.persistence.EmployeeDAO;
import gov.iti.persistence.ProjectDAO;
import gov.iti.rest.resources.project.ProjectRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class ProjectService {
    private static final ProjectDAO projectDAO = ProjectDAO.getInstance();

    public static List<Project> getAllProjects() {
        EntityManager entityManager = EntityManagerCreator.generateEntityManager();
        return projectDAO.findAll(entityManager);
    }

    public static Project getProjectByNumber(Integer projectNumber) {
        EntityManager entityManager = EntityManagerCreator.generateEntityManager();
        return projectDAO.findOneById(projectNumber, entityManager).orElse(null);
    }

    public static Project addProject(ProjectRequest projectRequest) {
        EntityManager entityManager = EntityManagerCreator.generateEntityManager();
        Project project = mapProjectRequestToProject(entityManager, projectRequest);
        System.out.println("mapped successfully");

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        try {
            projectDAO.create(entityManager, project);
            transaction.commit();
            return project;
        } catch (RuntimeException e) {
            e.printStackTrace();
            transaction.rollback();
            throw new RuntimeException(e);
        } finally {
            entityManager.close();
        }
    }

    public static Project getProject (Integer projectID) {
        EntityManager entityManager = EntityManagerCreator.generateEntityManager();
        return projectDAO.findOneById(projectID, entityManager).orElse(null);
    }

    public static Set<Project> getProjectsByIDs (EntityManager entityManager, Set<Integer> IDs) {
        return IDs.stream().map(id -> projectDAO.findOneById(id, entityManager).orElse(null)).collect(Collectors.toSet());
    }
    public static void updateProject(Integer projectNumber, ProjectRequest projectRequest) {
        EntityManager entityManager = EntityManagerCreator.generateEntityManager();

        Project project = projectDAO.findOneById(projectNumber, entityManager).orElse(null);
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        try {
            assert project != null;
            project.setProjectName(projectRequest.getProjectName());
            boolean hasEmployees = projectRequest.getEmployeesIDs() != null && !projectRequest.getEmployeesIDs().isEmpty();
            project.setEmployees( hasEmployees ?
                    projectRequest.getEmployeesIDs().stream()
                            .map(id -> {
                                Employee employee = entityManager.find(Employee.class, id);
                                employee.getProjects().add(project);
                                return employee;
                            }).collect(Collectors.toSet())
                    : null
            );
            projectDAO.update(entityManager, project);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            entityManager.close();
        }
    }

    private static Project mapProjectRequestToProject(EntityManager entityManager, ProjectRequest projectRequest) {
        Project project = new Project();
        project.setProjectName(projectRequest.getProjectName());

        boolean hasEmployees = projectRequest.getEmployeesIDs() != null && !projectRequest.getEmployeesIDs().isEmpty();
        project.setEmployees( hasEmployees ?
                projectRequest.getEmployeesIDs().stream()
                        .map(id -> {
                            Employee employee = entityManager.find(Employee.class, id);
                            employee.getProjects().add(project);
                            return employee;
                        }).collect(Collectors.toSet())
                : null
                );

        return project;
    }

    public static void deleteProjectByNumber(Integer projectNumber) {
        EntityManager entityManager = EntityManagerCreator.generateEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Project project = projectDAO.findOneById(projectNumber, entityManager).orElse(null);
        try {
            assert project != null;
            Set<Employee> employees = project.getEmployees();
            for (Employee employee : employees) {
                employee.getProjects().remove(project);
            }
            projectDAO.deleteById(entityManager, projectNumber);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            entityManager.close();
        }
    }
}
