package gov.iti.rest.resources.employee;

import gov.iti.business.entities.Employee;
import gov.iti.business.service.EmployeeService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.net.URI;
import java.util.List;

@Path("employees")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmployeeResource {
    @Context
    UriInfo uriInfo;
    @GET
    public Response getAllEmployees(@BeanParam EmployeeFilterBean filterBean) {
        List<Employee> returnedEmployees = null;
        if (filterBean != null && filterBean.hasFilters()){
            returnedEmployees = EmployeeService.getEmployeesFiltered(filterBean);
        } else {
            returnedEmployees = EmployeeService.getAllEmployees();
        }
        List<EmployeeResponse> employeeResponseList = returnedEmployees.stream().map(EmployeeResponse::new).toList();
        return Response.ok().entity(employeeResponseList).build();
    }

    @GET
    @Path("/{employeeID}")
    public Response getEmployeeByID (@PathParam("employeeID") Integer employeeID){
        Employee employee = EmployeeService.getEmployee(employeeID);
        return Response.ok().entity(new EmployeeResponse(employee)).build();
    }

    @POST
    public Response addEmployee (EmployeeRequest employeeRequest){
        Employee employee = EmployeeService.addEmployee(employeeRequest);
        String ID = String.valueOf(employee.getId());
        URI uri = uriInfo.getAbsolutePathBuilder().path(ID).build();
        return Response.created(uri).build();
    }

    @PUT
    @Path("/{employeeID}")
    public Response updateEmployee(@PathParam("employeeID") Integer employeeID, EmployeeRequest employeeRequest){
        EmployeeService.updateEmployee(employeeID, employeeRequest);
        return Response.accepted(uriInfo.getAbsolutePath()).build();
    }

    @DELETE
    @Path("/{employeeID}")
    public Response deleteEmployee(@PathParam("employeeID") Integer employeeID) {
        EmployeeService.deleteEmployeeByID(employeeID);
        return Response.ok("deletion successful").build();
    }
}