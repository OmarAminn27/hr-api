package gov.iti.rest.resources.project;

import gov.iti.business.entities.Department;
import gov.iti.business.entities.Project;
import gov.iti.business.service.DepartmentService;
import gov.iti.business.service.ProjectService;
import gov.iti.rest.resources.department.DepartmentRequest;
import gov.iti.rest.resources.department.DepartmentResponse;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.net.URI;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Path("projects")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProjectResource {
    @Context
    UriInfo uriInfo;

    @GET
    public Response getAllProjects() {
        List<Project> allProjects = ProjectService.getAllProjects();
        Set<ProjectResponse> projectResponses = allProjects.stream().map(ProjectResponse::new).collect(Collectors.toSet());
        return Response.ok().entity(projectResponses).build();
    }

    @GET
    @Path("/{projectNumber}")
    public Response getProjectByNumber (@PathParam("projectNumber") Integer projectNumber){
        Project projectByNumber = ProjectService.getProjectByNumber(projectNumber);
        ProjectResponse projectResponse = new ProjectResponse(projectByNumber);
        return Response.ok().entity(projectResponse).build();
    }

    @POST
    public Response addProject (ProjectRequest projectRequest){
        Project project = ProjectService.addProject(projectRequest);
        String projectNumber = String.valueOf(project.getProjectNumber());
        URI uri = uriInfo.getAbsolutePathBuilder().path(projectNumber).build();
        return Response.created(uri).build();
    }

    @PUT
    @Path("/{projectNumber}")
    public Response updateProject(@PathParam("projectNumber") Integer projectNumber, ProjectRequest projectRequest){
        ProjectService.updateProject(projectNumber, projectRequest);
        return Response.accepted(uriInfo.getAbsolutePath()).build();
    }

    @DELETE
    @Path("/{projectNumber}")
    public Response deleteProject(@PathParam("projectNumber") Integer projectNumber) {
        ProjectService.deleteProjectByNumber(projectNumber);
        return Response.ok("deletion successful").build();
    }
}
