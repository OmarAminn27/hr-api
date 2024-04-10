package gov.iti.rest.resources.employee;

import gov.iti.business.entities.Address;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequest implements Serializable {
    private Integer id;
    private Double salary;
    private String name;
    private LocalDate dateOfBirth;
    private Address address;
    private Integer managerID;
    private List<Integer> directReportsIDs;
    private Integer departmentID;
    private Set<Integer> projectsNumbers;
}
