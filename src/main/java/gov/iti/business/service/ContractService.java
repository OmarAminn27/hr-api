package gov.iti.business.service;

import gov.iti.business.entities.Contract;
import gov.iti.business.entities.Employee;
import gov.iti.business.entities.Project;
import gov.iti.business.util.EntityManagerCreator;
import gov.iti.persistence.ContractDAO;
import gov.iti.persistence.ProjectDAO;
import gov.iti.rest.resources.contract.ContractRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ContractService {
    private static final ContractDAO contractDAO = ContractDAO.getInstance();


    public static List<Contract> getAllContracts() {
        EntityManager entityManager = EntityManagerCreator.generateEntityManager();
        return contractDAO.findAll(entityManager);
    }

    public static Contract getContractByEmployeeID(Integer employeeID) {
        EntityManager entityManager = EntityManagerCreator.generateEntityManager();
        return contractDAO.findOneById(employeeID, entityManager).orElse(null);
    }

    public static Contract addContract(ContractRequest contractRequest) {
        EntityManager entityManager = EntityManagerCreator.generateEntityManager();
        Contract contract = mapContractRequestToContract(entityManager, contractRequest);
        System.out.println("mapped successfully");

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        try {
            contractDAO.create(entityManager, contract);
            transaction.commit();
            return contract;
        } catch (RuntimeException e) {
            e.printStackTrace();
            transaction.rollback();
            throw new RuntimeException(e);
        } finally {
            entityManager.close();
        }
    }

    private static Contract mapContractRequestToContract(EntityManager entityManager, ContractRequest contractRequest) {
        Contract contract = new Contract();
        contract.setContractType(contractRequest.getContractType());
        contract.setStartDate(contractRequest.getStartDate());
        contract.setEmployee(entityManager.find(Employee.class, contractRequest.getEmployeeID()));

        return contract;
    }

    public static void updateContract(Integer employeeID, ContractRequest contractRequest) {
        EntityManager entityManager = EntityManagerCreator.generateEntityManager();

        Contract contract = contractDAO.findOneById(employeeID, entityManager).orElse(null);
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        try {
            assert contract != null;
            contract.setContractType(contractRequest.getContractType());
            contract.setStartDate(contractRequest.getStartDate());
            contractDAO.update(entityManager, contract);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            entityManager.close();
        }
    }

    public static void deleteContractByEmployeeID(Integer employeeID) {
        EntityManager entityManager = EntityManagerCreator.generateEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Contract contract = contractDAO.findOneById(employeeID, entityManager).orElse(null);
        try {
            assert contract != null;
            contractDAO.deleteById(entityManager, employeeID);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            entityManager.close();
        }
    }
}
