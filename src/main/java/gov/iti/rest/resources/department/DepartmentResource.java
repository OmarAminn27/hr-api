package gov.iti.rest.resources.department;

import gov.iti.business.entities.Department;
import gov.iti.business.entities.Employee;
import gov.iti.business.service.DepartmentService;
import gov.iti.business.service.EmployeeService;
import gov.iti.rest.resources.employee.EmployeeFilterBean;
import gov.iti.rest.resources.employee.EmployeeRequest;
import gov.iti.rest.resources.employee.EmployeeResponse;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.net.URI;
import java.util.List;

@Path("departments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class DepartmentResource {
    @Context
    UriInfo uriInfo;

    @GET
    public Response getAllDepartments() {
        List<Department> allEmployees = DepartmentService.getAllDepartments();
        List<DepartmentResponse> departmentResponseList = allEmployees.stream().map(DepartmentResponse::new).toList();
        return Response.ok().entity(departmentResponseList).build();
    }

    @GET
    @Path("/{departmentNumber}")
    public Response getDepartmentByNumber (@PathParam("departmentNumber") Integer departmentNumber){
        Department departmentByNumber = DepartmentService.getDepartmentByNumber(departmentNumber);
        DepartmentResponse departmentResponse = new DepartmentResponse(departmentByNumber);
        return Response.ok().entity(departmentResponse).build();
    }

    @POST
    public Response addDepartment (DepartmentRequest departmentRequest){
        Department department = DepartmentService.addDepartment(departmentRequest);
        String departmentNumber = String.valueOf(department.getDepartmentNumber());
        URI uri = uriInfo.getAbsolutePathBuilder().path(departmentNumber).build();
        return Response.created(uri).build();
    }

    @PUT
    @Path("/{departmentNumber}")
    public Response updateDepartment(@PathParam("departmentNumber") Integer departmentNumber,
                                     DepartmentRequest departmentRequest){
        DepartmentService.updateDepartment(departmentNumber, departmentRequest);
        return Response.accepted(uriInfo.getAbsolutePath()).build();
    }

    @DELETE
    @Path("/{departmentNumber}")
    public Response deleteEmployee(@PathParam("departmentNumber") Integer departmentNumber) {
        DepartmentService.deleteDepartmentByNumber(departmentNumber);
        return Response.ok("deletion successful").build();
    }
}
