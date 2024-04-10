package gov.iti.rest.resources.contract;

import gov.iti.business.entities.Contract;
import gov.iti.business.entities.Project;
import gov.iti.business.service.ContractService;
import gov.iti.business.service.ProjectService;
import gov.iti.rest.resources.project.ProjectRequest;
import gov.iti.rest.resources.project.ProjectResponse;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.net.URI;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Path("contracts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ContractResource {
    @Context
    UriInfo uriInfo;

    @GET
    public Response getAllContracts() {
        List<Contract> allContracts = ContractService.getAllContracts();
        List<ContractResponse> contractResponses = allContracts.stream().map(ContractResponse::new).toList();
        return Response.ok().entity(contractResponses).build();
    }

    @GET
    @Path("/{employeeID}")
    public Response getContractByEmployeeID (@PathParam("employeeID") Integer employeeID){
        Contract contractByEmployeeID = ContractService.getContractByEmployeeID(employeeID);
        ContractResponse contractResponse = new ContractResponse(contractByEmployeeID);
        return Response.ok().entity(contractResponse).build();
    }

    @POST
    public Response addContract (ContractRequest contractRequest){
        Contract contract = ContractService.addContract(contractRequest);
        ContractResponse contractResponse = new ContractResponse(contract);
        return Response.ok().entity(contractResponse).build();
    }

    @PUT
    @Path("/{employeeID}")
    public Response updateContract(@PathParam("employeeID") Integer employeeID, ContractRequest contractRequest){
        ContractService.updateContract(employeeID, contractRequest);
        return Response.accepted(uriInfo.getAbsolutePath()).build();
    }

    @DELETE
    @Path("/{employeeID}")
    public Response deleteContract(@PathParam("employeeID") Integer employeeID) {
        ContractService.deleteContractByEmployeeID(employeeID);
        return Response.ok("deletion successful").build();
    }
}
