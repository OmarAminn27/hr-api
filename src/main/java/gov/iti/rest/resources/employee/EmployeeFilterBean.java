package gov.iti.rest.resources.employee;

import gov.iti.business.entities.Address;
import jakarta.ws.rs.QueryParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EmployeeFilterBean {
    private @QueryParam("salary") Double salary;
    private @QueryParam("name") String name;
    private @QueryParam("dateOfBirth") String dateOfBirth;
    private @QueryParam("address") Address address;
    private @QueryParam("managerID") Integer managerID;
    private @QueryParam("departmentID") Integer departmentID;

    public boolean hasFilters(){
        return (this.salary != null
                || this.name != null
                || this.dateOfBirth != null
                || this.address != null
                || this.managerID != null
                || this.departmentID != null);
    }
}
