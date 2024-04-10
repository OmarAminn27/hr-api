package gov.iti.rest.resources.contract;

import gov.iti.business.entities.Contract;
import gov.iti.business.entities.Employee;
import gov.iti.business.entities.Project;
import jakarta.json.bind.annotation.JsonbPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@JsonbPropertyOrder( {"employeeID", "contractType", "startDate"})
public class ContractResponse implements Serializable {
    private Integer employeeID;
    private LocalDate startDate;
    private String contractType;
    public ContractResponse (Contract contract){
        this.contractType = contract.getContractType();
        this.startDate = contract.getStartDate();
        this.employeeID = contract.getId();
    }
}
