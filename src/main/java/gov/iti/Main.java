package gov.iti;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import gov.iti.business.entities.Contract;
import gov.iti.business.entities.Department;
import gov.iti.business.entities.Employee;
import gov.iti.business.entities.Project;
import gov.iti.business.service.EmployeeService;
import gov.iti.business.util.EntityManagerCreator;
import gov.iti.persistence.EmployeeDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.util.List;

public class Main {
    public static void main(String[] args) throws JsonProcessingException {
        List<Employee> allEmployees = EmployeeService.getAllEmployees();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        System.out.println(objectMapper.writeValueAsString(allEmployees));
        System.out.println("hello");
    }
}
