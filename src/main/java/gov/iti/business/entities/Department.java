package gov.iti.business.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DNO")
    private Integer departmentNumber;

    @Column(name = "Location")
    private String location;

    @Column(name = "Department_name")
    private String departmentName;

    @OneToMany(mappedBy = "department",
            cascade = {CascadeType.PERSIST,
                        CascadeType.MERGE, CascadeType.REFRESH})
    private List<Employee> employees;

    @Override
    public String toString() {
        return "\nDepartment{" +
                "departmentNumber=" + departmentNumber +
                ", location='" + location + '\'' +
                ", departmentName='" + departmentName + '\'' +
                ", employees=" + employees.stream().map(Employee::getName).toList() +
                '}';
    }
}

// utility methods should work and update in both wayss!!
