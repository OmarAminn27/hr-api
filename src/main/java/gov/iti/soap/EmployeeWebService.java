package gov.iti.soap;

import gov.iti.business.entities.Department;
import gov.iti.business.entities.Employee;
import gov.iti.business.service.DepartmentService;
import gov.iti.business.service.EmployeeService;
import gov.iti.rest.resources.department.DepartmentResponse;
import gov.iti.rest.resources.employee.EmployeeFilterBean;
import gov.iti.rest.resources.employee.EmployeeRequest;
import gov.iti.rest.resources.employee.EmployeeResponse;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.net.URI;
import java.util.List;

@WebService
public class EmployeeWebService {

    @WebMethod
    @WebResult(name = "allEmployees")
    public List<EmployeeResponse> getAllEmployees() {
        List<Employee> allEmployees = EmployeeService.getAllEmployees();
        return allEmployees.stream().map(EmployeeResponse::new).toList();
    }

    @WebMethod
    @WebResult(name = "EmployeeByID")
    public EmployeeResponse getEmployeeByID (@WebParam(name = "employeeID") Integer employeeID){
        Employee employee = EmployeeService.getEmployee(employeeID);
        return new EmployeeResponse(employee);
    }

    @WebMethod
    @WebResult(name = "newlyAddedEmployee")
    public EmployeeResponse addEmployee (@WebParam(name = "employeeRequest") EmployeeRequest employeeRequest){
        Employee employee = EmployeeService.addEmployee(employeeRequest);
        return new EmployeeResponse(employee);
    }

    public void updateEmployee(@WebParam(name = "employeeID") Integer employeeID,
                                   @WebParam(name = "employeeRequest") EmployeeRequest employeeRequest){
        EmployeeService.updateEmployee(employeeID, employeeRequest);
    }

    public void deleteEmployee(@WebParam(name = "employeeID") Integer employeeID) {
        EmployeeService.deleteEmployeeByID(employeeID);
    }
}