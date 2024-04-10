package gov.iti.business.service;

import gov.iti.business.entities.Department;
import gov.iti.business.entities.Employee;
import gov.iti.business.util.EntityManagerCreator;
import gov.iti.persistence.DepartmentDAO;
import gov.iti.persistence.EmployeeDAO;
import gov.iti.rest.resources.department.DepartmentRequest;
import gov.iti.rest.resources.department.DepartmentResponse;
import gov.iti.rest.resources.employee.EmployeeRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;
import java.util.stream.Collectors;

public class DepartmentService {
    private static final DepartmentDAO departmentDAO = DepartmentDAO.getInstance();

    public static List<Department> getAllDepartments () {
        EntityManager entityManager = EntityManagerCreator.generateEntityManager();
        return departmentDAO.findAll(entityManager);
    }

    public static Department getDepartmentByNumber(Integer departmentNumber) {
        EntityManager entityManager = EntityManagerCreator.generateEntityManager();
        return departmentDAO.findOneById(departmentNumber, entityManager).orElse(null);
    }

    public static Department addDepartment(DepartmentRequest departmentRequest) {
        EntityManager entityManager = EntityManagerCreator.generateEntityManager();
        Department department = mapDepartmentRequestToDepartment(entityManager, departmentRequest);
        System.out.println("mapped successfully");

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        try {
            departmentDAO.create(entityManager, department);
            transaction.commit();
            return department;
        } catch (RuntimeException e) {
            e.printStackTrace();
            transaction.rollback();
            throw new RuntimeException(e);
        } finally {
            entityManager.close();
        }
    }

    public static void updateDepartment(Integer departmentNumber, DepartmentRequest departmentRequest) {
        EntityManager entityManager = EntityManagerCreator.generateEntityManager();
        Department department = mapDepartmentRequestToDepartment(entityManager, departmentRequest);
        department.setDepartmentNumber(departmentNumber);

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        try {
            departmentDAO.update(entityManager, department);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            entityManager.close();
        }
    }

    public static void deleteDepartmentByNumber(Integer departmentNumber) {
        EntityManager entityManager = EntityManagerCreator.generateEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Department department = departmentDAO.findOneById(departmentNumber, entityManager).orElse(null);
        try {
            List<Employee> employees = department.getEmployees();
            for (Employee employee : employees) {
                employee.setDepartment(null);
            }
            departmentDAO.deleteById(entityManager, departmentNumber);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            entityManager.close();
        }
    }

    private static Department mapDepartmentRequestToDepartment(EntityManager entityManager, DepartmentRequest departmentRequest) {
        Department department = new Department();
        department.setDepartmentName(departmentRequest.getDepartmentName());
        department.setLocation(departmentRequest.getLocation());

        boolean hasEmployees = departmentRequest.getEmployeesIDs() != null && !departmentRequest.getEmployeesIDs().isEmpty();
        department.setEmployees(hasEmployees ?
                departmentRequest.getEmployeesIDs().stream()
                        .map(id -> {
                            Employee employee = entityManager.find(Employee.class, id);
                            employee.setDepartment(department);
                            return employee;
                        }).toList()
                : null);
        return department;
    }
}
