package gov.iti.business.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Double salary;

    private String name;

    @Column(name = "dob")
    private LocalDate dateOfBirth;

    @Embedded
    private Address address;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    @JsonManagedReference
    private Employee manager;

    @OneToMany(mappedBy = "manager", cascade = {
                CascadeType.PERSIST, CascadeType.MERGE,
                CascadeType.REFRESH})
    @JsonBackReference
    private List<Employee> directReports;

    @ManyToOne
    @JoinColumn(name = "department_id")
    @JsonManagedReference
    private Department department;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH})
    @JoinTable(name = "employee_project",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "project_number"))
    @JsonManagedReference
    private Set<Project> projects;

    @Override
    public String toString() {
        return "\nEmployee{" +
                "id=" + id +
                ", salary=" + salary +
                ", name='" + name + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", address=" + address +
                ", manager=" + ((manager != null) ? manager.getName() : manager) +
                ", directReports=" + directReports.stream().map(Employee::getName).toList() +
                ", department=" + department.getDepartmentName() +
                ", projects=" + projects.stream().map(Project::getProjectName).toList() +
                '}';
    }

}
