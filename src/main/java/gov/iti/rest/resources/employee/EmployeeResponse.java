package gov.iti.rest.resources.employee;

import gov.iti.business.entities.Address;
import gov.iti.business.entities.Employee;
import gov.iti.business.entities.Project;
import jakarta.json.bind.annotation.JsonbPropertyOrder;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Getter
@Setter
@ToString
@JsonbPropertyOrder( {"id", "name", "salary", "dateOfBirth", "address", "managerID",
        "directReportsIDs", "departmentID", "projectNumbers"} )
public class EmployeeResponse implements Serializable {
    private Integer id;
    private Double salary;
    private String name;
    private LocalDate dateOfBirth;
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

        this.departmentID = employee.getDepartment() != null ? employee.getDepartment().getDepartmentNumber() : null;

        this.projectsNumbers = (employee.getProjects() != null && !employee.getProjects().isEmpty()) ?
                                    employee.getProjects().stream().map(Project::getProjectNumber).collect(Collectors.toSet())
                                    : null;
    }

}
