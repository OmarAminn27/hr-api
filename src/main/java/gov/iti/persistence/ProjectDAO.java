package gov.iti.persistence;

import gov.iti.business.entities.Contract;
import gov.iti.business.entities.Project;

public class ProjectDAO extends AbstractDAO<Project>{
    private static final ProjectDAO ONLY_INSTANCE = new ProjectDAO();
    private ProjectDAO() {
        super(Project.class);
    }

    public static ProjectDAO getInstance() {
        return ONLY_INSTANCE;
    }
}