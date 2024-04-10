package gov.iti.rest.resources.department;

import com.fasterxml.jackson.annotation.JsonBackReference;
import gov.iti.business.entities.Address;
import gov.iti.business.entities.Department;
import gov.iti.business.entities.Employee;
import gov.iti.business.entities.Project;
import jakarta.json.bind.annotation.JsonbPropertyOrder;
import jakarta.persistence.*;
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
@JsonbPropertyOrder( {"departmentNumber", "departmentName", "location", "employeesIDs"})
public class DepartmentResponse implements Serializable {
    private Integer departmentNumber;
    private String location;
    private String departmentName;
    private List<Integer> employeesIDs;
    public DepartmentResponse (Department department){
        this.departmentNumber = department.getDepartmentNumber();
        this.location = department.getLocation();
        this.departmentName = department.getDepartmentName();

        boolean hasEmployees = department.getEmployees() != null && !department.getEmployees().isEmpty();
        this.employeesIDs = hasEmployees ? department.getEmployees()
                                            .stream().map(Employee::getId).toList()
                                            : null;
    }
}
