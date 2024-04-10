package gov.iti.business.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Collate;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Project_number")
    private Integer projectNumber;

    @Column(name = "Project_name")
    private String projectName;

    @JsonBackReference
    @ManyToMany(mappedBy = "projects")
    private Set<Employee> employees;

    @Override
    public String toString() {
        return "\nProject{" +
                "projectNumber=" + projectNumber +
                ", projectName='" + projectName + '\'' +
                ", employees=" + employees.stream().map(Employee::getName).toList() +
                '}';
    }
}
