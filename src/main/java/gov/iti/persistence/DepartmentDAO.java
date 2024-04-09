package gov.iti.persistence;

import gov.iti.business.entities.Contract;
import gov.iti.business.entities.Department;

public class DepartmentDAO extends AbstractDAO<Department>{
    private static final DepartmentDAO ONLY_INSTANCE = new DepartmentDAO();
    private DepartmentDAO() {
        super(Department.class);
    }

    public static DepartmentDAO getInstance() {
        return ONLY_INSTANCE;
    }
}