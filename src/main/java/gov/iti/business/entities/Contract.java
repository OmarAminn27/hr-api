package gov.iti.business.entities;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@Setter
public class Contract {
    @Id
    @Column(name = "employee_id")
    private Integer id;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "contract_type")
    private String contractType;

    @MapsId
    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH, CascadeType.REFRESH})
    private Employee employee;

    @Override
    public String toString() {
        return "\nContract{" +
                "startDate=" + startDate +
                ", contractType='" + contractType + '\'' +
                ", employee=" + employee.getName() +
                '}';
    }
}
