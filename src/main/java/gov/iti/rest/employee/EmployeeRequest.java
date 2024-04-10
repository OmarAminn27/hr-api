package gov.iti.rest.employee;

import gov.iti.business.entities.Address;
import gov.iti.business.entities.Employee;
import gov.iti.business.entities.Project;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequest implements Serializable {
    private Integer id;
    private Double salary;
    private String name;
    private Date dateOfBirth;
    private Address address;
    private Integer managerID;
    private List<Integer> directReportsIDs;
    private Integer departmentID;
    private Set<Integer> projectsNumbers;
}
