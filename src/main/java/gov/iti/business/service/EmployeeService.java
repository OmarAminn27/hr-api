package gov.iti.business.service;

import gov.iti.business.entities.Department;
import gov.iti.business.entities.Employee;
import gov.iti.business.entities.Project;
import gov.iti.business.util.EntityManagerCreator;
import gov.iti.persistence.EmployeeDAO;
import gov.iti.rest.employee.EmployeeRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.util.List;

public class EmployeeService {

    private static final EmployeeDAO employeeDAO = EmployeeDAO.getInstance();


    public static Employee getEmployee (Integer employeeID) {
        EntityManager entityManager = EntityManagerCreator.generateEntityManager();
        return employeeDAO.findOneById(employeeID, entityManager).orElse(null);
    }

    public static List<Employee> getAllEmployees () {
        EntityManager entityManager = EntityManagerCreator.generateEntityManager();
        return employeeDAO.findAll(entityManager);
    }

    public static List<Employee> getEmployeesByIDs(EntityManager entityManager, List<Integer> IDs) {
        return IDs.stream().map(EmployeeService::getEmployee).toList();
    }

    public static void deleteEmployee(Employee employee){
        Integer employeeId = employee.getId();
        EntityManager entityManager = EntityManagerCreator.generateEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        try {
            employeeDAO.deleteById(entityManager, employeeId);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            entityManager.close();
        }
    }

    public static void deleteEmployeeByID(Integer employeeID){
        EntityManager entityManager = EntityManagerCreator.generateEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        try {
            employeeDAO.deleteById(entityManager, employeeID);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            entityManager.close();
        }
    }

    public static void addEmployee(EmployeeRequest employeeRequest) {
        EntityManager entityManager = EntityManagerCreator.generateEntityManager();
        Employee employee = mapEmployeeRequestToEmployee(entityManager, employeeRequest);

        System.out.println("mapped successfully");

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        try {
            employeeDAO.create(entityManager, employee);
            transaction.commit();
        } catch (RuntimeException e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            entityManager.close();
        }
    }

    private static Employee mapEmployeeRequestToEmployee(EntityManager entityManager, EmployeeRequest employeeRequest) {
        Employee employee = new Employee();
        employee.setSalary(employeeRequest.getSalary());
        employee.setName(employeeRequest.getName());
        employee.setDateOfBirth(employeeRequest.getDateOfBirth());
        employee.setAddress(employeeRequest.getAddress());

        employee.setManager((employeeRequest.getManagerID() != null) ?
                                EmployeeService.getEmployee(employeeRequest.getManagerID()): null);

        boolean hasDirectReports = employeeRequest.getDirectReportsIDs() != null && !employeeRequest.getDirectReportsIDs().isEmpty();
        employee.setDirectReports(hasDirectReports ?
                EmployeeService.getEmployeesByIDs(entityManager, employeeRequest.getDirectReportsIDs())
                : null );

        Department department = entityManager.find(Department.class, employeeRequest.getDepartmentID());
        employee.setDepartment(department);

        boolean hasProjects = employeeRequest.getProjectsNumbers() != null && !employeeRequest.getProjectsNumbers().isEmpty();
        employee.setProjects(hasProjects ?
                            new ProjectService().getProjectsByIDs(entityManager, employeeRequest.getProjectsNumbers())
                            : null
        );

        return employee;
    }

    public static void updateEmployee(Integer employeeID, EmployeeRequest employeeRequest) {
        EntityManager entityManager = EntityManagerCreator.generateEntityManager();
        Employee employee = mapEmployeeRequestToEmployee(entityManager, employeeRequest);
        employee.setId(employeeID);

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        try {
            employeeDAO.update(entityManager, employee);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            entityManager.close();
        }
    }
}
