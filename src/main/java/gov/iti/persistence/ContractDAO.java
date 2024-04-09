package gov.iti.persistence;

import gov.iti.business.entities.Contract;
import gov.iti.business.entities.Employee;

public class ContractDAO extends AbstractDAO<Contract>{
    private static final ContractDAO ONLY_INSTANCE = new ContractDAO();
    private ContractDAO() {
        super(Contract.class);
    }

    public static ContractDAO getInstance() {
        return ONLY_INSTANCE;
    }
}