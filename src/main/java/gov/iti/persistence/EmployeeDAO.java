package gov.iti.persistence;

import gov.iti.business.entities.Employee;

public class EmployeeDAO extends AbstractDAO<Employee>{
    private static final EmployeeDAO ONLY_INSTANCE = new EmployeeDAO();
    private EmployeeDAO() {
        super(Employee.class);
    }

    public static EmployeeDAO getInstance() {
        return ONLY_INSTANCE;
    }
}
