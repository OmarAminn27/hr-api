package gov.iti.rest.resources.contract;

import gov.iti.business.entities.Employee;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ContractRequest implements Serializable {
    private Integer employeeID;
    private LocalDate startDate;
    private String contractType;
}