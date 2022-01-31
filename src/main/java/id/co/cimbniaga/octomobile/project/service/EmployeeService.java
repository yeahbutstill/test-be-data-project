package id.co.cimbniaga.octomobile.project.service;

import id.co.cimbniaga.octomobile.project.domain.dto.external.EmployeeDtoRequest;
import org.springframework.http.ResponseEntity;

public interface EmployeeService {

    ResponseEntity<Object> getEmployee(String request);

    ResponseEntity<Object> insertEmployee(EmployeeDtoRequest request);

    ResponseEntity<Object> updateEmployee(EmployeeDtoRequest request);

    ResponseEntity<Object> deleteEmployee(Long request);

    ResponseEntity<Object> listEmployee();

}
