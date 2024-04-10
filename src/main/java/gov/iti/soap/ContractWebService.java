package gov.iti.soap;

import gov.iti.business.entities.Contract;
import gov.iti.business.service.ContractService;
import gov.iti.rest.resources.contract.ContractRequest;
import gov.iti.rest.resources.contract.ContractResource;
import gov.iti.rest.resources.contract.ContractResponse;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.util.List;

@WebService
public class ContractWebService {

    @WebMethod
    @WebResult(name = "allContracts")
    public List<ContractResponse> getAllContracts() {
        List<Contract> allContracts = ContractService.getAllContracts();
        return allContracts.stream().map(ContractResponse::new).toList();
    }

    @WebMethod
    @WebResult(name = "ContractByEmployeeID")
    public ContractResponse getContractByEmployeeID (@WebParam(name = "employeeID") Integer employeeID){
        Contract contractByEmployeeID = ContractService.getContractByEmployeeID(employeeID);
        return new ContractResponse(contractByEmployeeID);
    }

    @WebMethod
    @WebResult(name = "newlyAddedContract")
    public ContractResponse addContract (ContractRequest contractRequest){
        Contract contract = ContractService.addContract(contractRequest);
        return new ContractResponse(contract);
    }

    public void updateContract(@WebParam(name = "employeeID") Integer employeeID,
                               @WebParam(name = "contractRequest") ContractRequest contractRequest){
        ContractService.updateContract(employeeID, contractRequest);
    }


    public void deleteContract(@WebParam(name = "employeeID") Integer employeeID) {
        ContractService.deleteContractByEmployeeID(employeeID);
    }
}

