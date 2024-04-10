package gov.iti.rest.resources.project;

import com.fasterxml.jackson.annotation.JsonBackReference;
import gov.iti.business.entities.Employee;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToMany;
import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProjectRequest implements Serializable {
    private Integer projectNumber;
    private String projectName;
    private Set<Integer> employeesIDs;
}
