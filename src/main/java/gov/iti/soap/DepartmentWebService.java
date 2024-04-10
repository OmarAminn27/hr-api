package gov.iti.soap;

import gov.iti.business.entities.Department;
import gov.iti.business.service.DepartmentService;
import gov.iti.rest.resources.department.DepartmentRequest;
import gov.iti.rest.resources.department.DepartmentResponse;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@WebService
public class DepartmentWebService {

    @WebMethod
    @WebResult(name = "allDepartments")
    public List<DepartmentResponse> getAllDepartments() {
        List<Department> allDepartments = DepartmentService.getAllDepartments();
        return allDepartments.stream().map(DepartmentResponse::new).toList();
    }

    @WebMethod
    @WebResult(name = "newlyAddedDepartment")
    public DepartmentResponse addDepartment (@WebParam(name = "departmentRequest") DepartmentRequest departmentRequest){
        Department department = DepartmentService.addDepartment(departmentRequest);
        return new DepartmentResponse(department);
    }

    @WebMethod
    public void deleteDepartment(@WebParam(name = "departmentNumber") Integer departmentNumber) {
        DepartmentService.deleteDepartmentByNumber(departmentNumber);
    }

    @WebMethod
    @WebResult(name = "departmentByNumber")
    public DepartmentResponse getDepartmentByNumber (@WebParam(name = "departmentNumber") Integer departmentNumber){
        Department departmentByNumber = DepartmentService.getDepartmentByNumber(departmentNumber);
        return new DepartmentResponse(departmentByNumber);
    }


    @WebMethod
    public void updateDepartment(@WebParam(name = "departmentNumber") Integer departmentNumber,
                                 @WebParam(name = "departmentRequest") DepartmentRequest departmentRequest){
        DepartmentService.updateDepartment(departmentNumber, departmentRequest);
    }
}
