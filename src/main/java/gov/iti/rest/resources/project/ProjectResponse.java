package gov.iti.rest.resources.project;

import com.fasterxml.jackson.annotation.JsonBackReference;
import gov.iti.business.entities.Department;
import gov.iti.business.entities.Employee;
import gov.iti.business.entities.Project;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
public class ProjectResponse implements Serializable {
    private Integer projectNumber;
    private String projectName;
    private Set<Integer> employeesIDs;
    public ProjectResponse (Project project){
        this.projectNumber = project.getProjectNumber();
        this.projectName = project.getProjectName();

        boolean hasEmployees = project.getEmployees() != null && !project.getEmployees().isEmpty();
        this.employeesIDs = hasEmployees ?
                project.getEmployees().stream().map(Employee::getId).collect(Collectors.toSet())
                : null;
    }
}
