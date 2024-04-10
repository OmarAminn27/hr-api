package gov.iti.business.service;

import gov.iti.business.entities.Contract;
import gov.iti.business.entities.Department;
import gov.iti.business.entities.Employee;
import gov.iti.business.entities.Project;
import gov.iti.business.util.EntityManagerCreator;
import gov.iti.persistence.EmployeeDAO;
import gov.iti.rest.resources.employee.EmployeeFilterBean;
import gov.iti.rest.resources.employee.EmployeeRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmployeeService {

    private static final EmployeeDAO employeeDAO = EmployeeDAO.getInstance();


    public static Employee getEmployee(Integer employeeID) {
        EntityManager entityManager = EntityManagerCreator.generateEntityManager();
        return employeeDAO.findOneById(employeeID, entityManager).orElse(null);
    }

    public static List<Employee> getAllEmployees() {
        EntityManager entityManager = EntityManagerCreator.generateEntityManager();
        return employeeDAO.findAll(entityManager);
    }

    public static List<Employee> getEmployeesFiltered(EmployeeFilterBean filterBean) {
        EntityManager entityManager = EntityManagerCreator.generateEntityManager();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
        Root<Employee> root = criteriaQuery.from(Employee.class);

        List<Predicate> predicates = new ArrayList<>();

        if (filterBean.getSalary() != null) {
            predicates.add(criteriaBuilder.equal(root.get("salary"), filterBean.getSalary()));
        }

        if (filterBean.getName() != null) {
            predicates.add(criteriaBuilder.equal(root.get("name"), filterBean.getName()));
        }

        if (filterBean.getDepartmentID() != null) {
            predicates.add(criteriaBuilder.equal(root.get("department").get("departmentNumber"), filterBean.getDepartmentID()));
        }

        if (filterBean.getManagerID() != null) {
            predicates.add(criteriaBuilder.equal(root.get("manager").get("id"), filterBean.getManagerID()));
        }

        if (filterBean.getDateOfBirth() != null) {
            LocalDate localDate = LocalDate.parse(filterBean.getDateOfBirth());
            predicates.add(criteriaBuilder.equal(root.get("dateOfBirth"), localDate));
        }

        if (filterBean.getAddress() != null) {
            predicates.add(criteriaBuilder.equal(root.get("address"), filterBean.getAddress()));
        }

        criteriaQuery.where(predicates.toArray(new Predicate[0]));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    public static List<Employee> getEmployeesByIDs(EntityManager entityManager, List<Integer> IDs) {
        return IDs.stream().map(id -> employeeDAO.findOneById(id, entityManager).orElse(null)).toList();
    }

    public static void deleteEmployee(Employee employee) {
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

    public static void deleteEmployeeByID(Integer employeeID) {
        EntityManager entityManager = EntityManagerCreator.generateEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        try {
            // remove employee's reference in projects
            Employee employee = employeeDAO.findOneById(employeeID, entityManager).orElse(null);
            assert employee != null;
            for (Project project : employee.getProjects()) {
                project.getEmployees().remove(employee);
            }

            // remove employee's reference in departments
            employee.getDepartment().getEmployees().remove(employee);

            // remove employee's reference in directReports
            for (Employee directReport : employee.getDirectReports()) {
                directReport.setManager(null);
            }

            // remove employee's reference from their direct manager's list
            Employee manager = employee.getManager();
            if (manager != null) {
                List<Employee> directReports = manager.getDirectReports();
                for (Employee directReport : directReports) {
                    directReport.setManager(null);
                }
            }

            // delete employee's contract
            Contract contract = entityManager.find(Contract.class, employee.getId());
            entityManager.remove(contract);

            employeeDAO.deleteById(entityManager, employeeID);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            entityManager.close();
        }
    }

    public static Employee addEmployee(EmployeeRequest employeeRequest) {
        EntityManager entityManager = EntityManagerCreator.generateEntityManager();
        Employee employee = mapEmployeeRequestToEmployee(entityManager, employeeRequest);
        System.out.println("mapped successfully");
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        try {
            employeeDAO.create(entityManager, employee);
            transaction.commit();
            return employee;
        } catch (RuntimeException e) {
            e.printStackTrace();
            transaction.rollback();
            throw new RuntimeException(e);
        } finally {
            entityManager.close();
        }
    }

    public static void updateEmployee(Integer employeeID, EmployeeRequest employeeRequest) {
        EntityManager entityManager = EntityManagerCreator.generateEntityManager();
        Employee employee = employeeDAO.findOneById(employeeID, entityManager).orElse(null);
        
        assert employee != null;
        mapperHelper(employeeRequest, employee, entityManager);

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

    private static Employee mapEmployeeRequestToEmployee(EntityManager entityManager, EmployeeRequest employeeRequest) {
        Employee employee = new Employee();
        mapperHelper(employeeRequest, employee, entityManager);
        return employee;
    }

    private static void mapperHelper(EmployeeRequest employeeRequest, Employee employee, EntityManager entityManager) {
        employee.setSalary(employeeRequest.getSalary());
        employee.setName(employeeRequest.getName());
        employee.setDateOfBirth(employeeRequest.getDateOfBirth());
        employee.setAddress(employeeRequest.getAddress());

        employee.setManager((employeeRequest.getManagerID() != null) ?
                employeeDAO.findOneById(employeeRequest.getManagerID(), entityManager).orElse(null) : null);

        boolean hasDirectReports = employeeRequest.getDirectReportsIDs() != null && !employeeRequest.getDirectReportsIDs().isEmpty();
        employee.setDirectReports(hasDirectReports ?
                getEmployeesByIDs(entityManager, employeeRequest.getDirectReportsIDs())
                : null);

        Department department = entityManager.find(Department.class, employeeRequest.getDepartmentID());
        employee.setDepartment(department);

        boolean hasProjects = employeeRequest.getProjectsNumbers() != null && !employeeRequest.getProjectsNumbers().isEmpty();
        employee.setProjects(hasProjects ?
                ProjectService.getProjectsByIDs(entityManager, employeeRequest.getProjectsNumbers())
                : null
        );
    }
}
