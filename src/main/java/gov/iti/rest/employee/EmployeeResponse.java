package gov.iti.rest.employee;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import gov.iti.business.entities.Address;
import gov.iti.business.entities.Department;
import gov.iti.business.entities.Employee;
import gov.iti.business.entities.Project;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Getter
@Setter
@ToString
public class EmployeeResponse implements Serializable {
    private Integer id;
    private Double salary;
    private String name;
    private Date dateOfBirth;
    private Address address;
    private Integer managerID;
    private List<Integer> directReportsIDs;
    private Integer departmentID;
    private Set<Integer> projectsNumbers;
    public EmployeeResponse (Employee employee){
        this.id = employee.getId();
        this.salary = employee.getSalary();
        this.dateOfBirth = employee.getDateOfBirth();
        this.name = employee.getName();
        this.address = employee.getAddress();

        this.managerID = (employee.getManager() != null) ? employee.getManager().getId()
                                                            : null;

        this.directReportsIDs = (employee.getProjects() != null && !employee.getProjects().isEmpty()) ?
                                    employee.getDirectReports().stream().map(Employee::getId).toList()
                                    : null;

        this.departmentID = employee.getDepartment().getDepartmentNumber();

        this.projectsNumbers = (employee.getProjects() != null && !employee.getProjects().isEmpty()) ?
                                    employee.getProjects().stream().map(Project::getProjectNumber).collect(Collectors.toSet())
                                    : null;
    }

}
