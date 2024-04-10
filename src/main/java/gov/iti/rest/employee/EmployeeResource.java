package gov.iti.rest.employee;

import gov.iti.business.entities.Employee;
import gov.iti.business.service.EmployeeService;
import gov.iti.business.util.EntityManagerCreator;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("employees")

public class EmployeeResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<EmployeeResponse> getAllEmployees() {
        List<Employee> allEmployees = new EmployeeService().getAllEmployees();
        return allEmployees.stream().map(EmployeeResponse::new).toList();
    }

    @GET
    @Path("/{employeeID}")
    @Produces(MediaType.APPLICATION_JSON)
    public EmployeeResponse getEmployeeByID (@PathParam("employeeID") Integer employeeID){
        Employee employee = EmployeeService.getEmployee(employeeID);
        return new EmployeeResponse(employee);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void addEmployee (EmployeeRequest employeeRequest) {
        EmployeeService.addEmployee(employeeRequest);
    }

    @DELETE
    @Path("/{employeeID}")
    public void deleteEmployee(@PathParam("employeeID") Integer employeeID) {
        EmployeeService.deleteEmployeeByID(employeeID);
    }

    @PUT
    @Path("/{employeeID}")
    public void updateEmployee(@PathParam("employeeID") Integer employeeID, EmployeeRequest employeeRequest){
        EmployeeService.updateEmployee(employeeID, employeeRequest);
    }
}