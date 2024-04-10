package gov.iti.soap;

import gov.iti.business.entities.Department;
import gov.iti.business.service.DepartmentService;
import gov.iti.rest.resources.department.DepartmentRequest;
import gov.iti.rest.resources.department.DepartmentResponse;
import jakarta.jws.WebMethod;
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
    public DepartmentResponse addDepartment (DepartmentRequest departmentRequest){
        Department department = DepartmentService.addDepartment(departmentRequest);
        return new DepartmentResponse(department);
    }

    @WebMethod
    public void deleteDepartment(Integer departmentNumber) {
        DepartmentService.deleteDepartmentByNumber(departmentNumber);
    }

    @WebMethod
    @WebResult(name = "departmentByNumber")
    public DepartmentResponse getDepartmentByNumber (Integer departmentNumber){
        Department departmentByNumber = DepartmentService.getDepartmentByNumber(departmentNumber);
        return new DepartmentResponse(departmentByNumber);
    }


    @WebMethod
    public void updateDepartment(Integer departmentNumber, DepartmentRequest departmentRequest){
        DepartmentService.updateDepartment(departmentNumber, departmentRequest);
    }
}
