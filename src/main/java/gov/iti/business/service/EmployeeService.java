package gov.iti.business.service;

import gov.iti.business.entities.Employee;
import gov.iti.business.util.EntityManagerCreator;
import gov.iti.persistence.EmployeeDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class EmployeeService {

    private final EmployeeDAO employeeDAO = EmployeeDAO.getInstance();


    public Employee getEmployee (Integer employeeID) {
        EntityManager entityManager = EntityManagerCreator.generateEntityManager();
        return employeeDAO.findOneById(employeeID, entityManager).orElse(null);
    }

    public List<Employee> getAllEmployees () {
        EntityManager entityManager = EntityManagerCreator.generateEntityManager();
        return employeeDAO.findAll(entityManager);
    }

    public void deleteEmployee(Employee employee){
        Integer employeeId = employee.getId();
        EntityManager entityManager = EntityManagerCreator.generateEntityManager();
        employeeDAO.deleteById(entityManager, employeeId);
    }
}
