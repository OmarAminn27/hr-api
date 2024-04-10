package gov.iti.rest.resources.department;


import com.fasterxml.jackson.annotation.JsonBackReference;
import gov.iti.business.entities.Employee;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentRequest implements Serializable {
    private Integer departmentNumber;
    private String location;
    private String departmentName;
    private List<Integer> employeesIDs;
}
