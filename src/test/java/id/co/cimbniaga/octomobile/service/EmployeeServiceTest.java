package id.co.cimbniaga.octomobile.service;

import id.co.cimbniaga.octomobile.project.domain.dao.Employee;
import id.co.cimbniaga.octomobile.project.domain.dto.external.EmployeeDtoRequest;
import id.co.cimbniaga.octomobile.project.repository.EmployeeRepository;
import id.co.cimbniaga.octomobile.project.service.implementation.EmployeeServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@Slf4j
@SpringBootTest
class EmployeeServiceTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Mock
    private ModelMapper modelMapper;

    public Employee getEmployee() {
        return Employee.builder()
                .id(1L)
                .firstName("Dede")
                .lastName("Hamzah")
                .nik("AB1234")
                .gender("laki-laki")
                .salary(1000000L)
                .title("Programmer").build();
    }

    @Test
    void getListEmployee_expectSuccess() {
        List<Employee> employeesList = List.of(getEmployee());
        Mockito.when(employeeRepository.findAll()).thenReturn(employeesList);
        ResponseEntity<Object> responseEntity = employeeService.listEmployee();
        Assertions.assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    void getEmployee_expectSuccess() {
        Mockito.when(employeeRepository.findByNik(any())).thenReturn(getEmployee());
        ResponseEntity<Object> response = employeeService.getEmployee(any());
        Assertions.assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void insertEmployee_expectSuccess() {
        ResponseEntity<Object> response = employeeService.insertEmployee(
                EmployeeDtoRequest.builder()
                        .firstName("Dede")
                        .lastName("Hamzah")
                        .nik("AB1234")
                        .gender("laki-laki")
                        .salary(1000000L)
                        .title("Programmer")
                        .build());
        Assertions.assertEquals(200, response.getStatusCodeValue());
        Mockito.verify(employeeRepository).save(any());
    }

    @Test
    void updateEmployee_expectSuccess() {
        ResponseEntity<Object> response = employeeService.updateEmployee(
                EmployeeDtoRequest.builder()
                        .id(1L)
                        .firstName("Dede")
                        .lastName("Hamzah")
                        .nik("AB1234")
                        .gender("laki-laki")
                        .salary(1000000L)
                        .title("Programmer")
                        .build());
        Assertions.assertEquals(200, response.getStatusCodeValue());
        Mockito.verify(employeeRepository).save(any());
    }

    @Test
    void deleteEmployee_expectSuccess() {
        Mockito.when(employeeRepository.findById(any())).thenReturn(Optional.of(getEmployee()));
        ResponseEntity<Object> response = employeeService.deleteEmployee(getEmployee().getId());
        Assertions.assertEquals(200, response.getStatusCodeValue());
        Mockito.verify(employeeRepository).delete(any());
    }

    @Test
    void deleteEmployee_expectError() {
        Mockito.when(employeeRepository.findById(any())).thenReturn(Optional.empty());
        ResponseEntity<Object> response = employeeService.deleteEmployee(getEmployee().getId());
        Assertions.assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void getListEmployee_expectError() {
        Mockito.when(employeeRepository.findAll()).thenReturn(new ArrayList<>());
        ResponseEntity<Object> response = employeeService.listEmployee();
        Assertions.assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void updateEmployee_expectError() {
        ResponseEntity<Object> response = employeeService.updateEmployee(EmployeeDtoRequest.builder()
                .firstName("Dede")
                .lastName("Hamzah")
                .nik("AB1234")
                .gender("laki-laki")
                .salary(1000000L)
                .title("Programmer")
                .build());
        Assertions.assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void insertEmployee_expectError() {
        ResponseEntity<Object> response = employeeService.insertEmployee(EmployeeDtoRequest.builder()
                .id(1L)
                .firstName("Dede")
                .lastName("Hamzah")
                .nik("AB1234")
                .gender("laki-laki")
                .salary(1000000L)
                .title("Programmer")
                .build());
        Assertions.assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void getEmployee_expectError() {
        Mockito.when(employeeRepository.findByNik(any())).thenReturn(null);
        ResponseEntity<Object> response = employeeService.getEmployee(any());
        Assertions.assertEquals(404, response.getStatusCodeValue());
    }
}
