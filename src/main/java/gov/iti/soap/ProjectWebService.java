package gov.iti.soap;

import gov.iti.business.entities.Project;
import gov.iti.business.service.ProjectService;
import gov.iti.rest.resources.project.ProjectRequest;
import gov.iti.rest.resources.project.ProjectResponse;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.net.URI;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@WebService
public class ProjectWebService {

    @WebMethod
    @WebResult(name = "allProjects")
    public Set<ProjectResponse> getAllProjects() {
        List<Project> allProjects = ProjectService.getAllProjects();
        return allProjects.stream().map(ProjectResponse::new).collect(Collectors.toSet());
    }

    @WebMethod
    @WebResult(name = "projectByNumber")
    public ProjectResponse getProjectByNumber (@WebParam(name = "projectNumber") Integer projectNumber){
        Project projectByNumber = ProjectService.getProjectByNumber(projectNumber);
        return new ProjectResponse(projectByNumber);
    }

    @WebMethod
    @WebResult(name = "newlyAddedProject")
    public ProjectResponse addProject (@WebParam(name = "projectRequest") ProjectRequest projectRequest){
        Project project = ProjectService.addProject(projectRequest);
        return new ProjectResponse(project);
    }

    public void updateProject(@WebParam(name = "projectNumber") Integer projectNumber,
                              @WebParam(name = "projectRequest") ProjectRequest projectRequest){
        ProjectService.updateProject(projectNumber, projectRequest);
    }


    public void deleteProject(@WebParam(name = "projectNumber") Integer projectNumber) {
        ProjectService.deleteProjectByNumber(projectNumber);
    }
}
