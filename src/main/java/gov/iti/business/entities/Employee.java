package gov.iti.business.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Double salary;

    private String name;

    @Column(name = "dob")
    private Date dateOfBirth;

    @Embedded
    private Address address;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Employee manager;

    @OneToMany(mappedBy = "manager", cascade = {
                CascadeType.PERSIST, CascadeType.MERGE,
                CascadeType.REFRESH})
    private List<Employee> directReports;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST,
                            CascadeType.REFRESH})
    @JoinTable(name = "employee_project",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "project_number"))
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


// utility methods project
