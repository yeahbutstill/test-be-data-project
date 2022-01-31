package id.co.cimbniaga.octomobile.project.service.implementation;

import id.co.cimbniaga.octomobile.project.constant.ConstantMess;
import id.co.cimbniaga.octomobile.project.domain.dao.Employee;
import id.co.cimbniaga.octomobile.project.domain.dto.common.BaseResponse;
import id.co.cimbniaga.octomobile.project.domain.dto.external.EmployeeDtoRequest;
import id.co.cimbniaga.octomobile.project.domain.dto.external.EmployeeDtoResponse;
import id.co.cimbniaga.octomobile.project.repository.EmployeeRepository;
import id.co.cimbniaga.octomobile.project.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final ModelMapper modelMapper;
    private final EmployeeRepository employeeRepository;

    @Override
    public ResponseEntity<Object> getEmployee(String request) {

        log.info("begin get employee, with NIK {}", request);

        EmployeeDtoResponse employeeDtoResponse = null;
        try {
            Optional<Employee> employee = Optional.ofNullable(employeeRepository.findByNik(request));
            if (employee.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BaseResponse.builder()
                                .localDateTime(LocalDateTime.now())
                                .data(null)
                                .message(ConstantMess.KEY_DATA_NOT_FOUND)
                                .statusCode(HttpStatus.NO_CONTENT.value())
                                .build());
            }

            employeeDtoResponse = new EmployeeDtoResponse(employee.get());
        } catch (Exception exception) {
            log.error("failed get employee with nik, Error :  " + exception);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BaseResponse.builder()
                        .localDateTime(LocalDateTime.now())
                        .data(employeeDtoResponse)
                        .message(ConstantMess.KEY_SUCCESS)
                        .statusCode(HttpStatus.OK.value())
                        .build());

    }

    @Override
    public ResponseEntity<Object> insertEmployee(EmployeeDtoRequest request) {
        log.info("begin insert employee, with param {}", request);

        if (request != null) {
            if (request.getId() != null) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BaseResponse.builder()
                                .message(ConstantMess.KEY_INVALID_REQUEST)
                                .data(request).build());
            }

            try {
                Employee employees = modelMapper.map(request, Employee.class);

                employeeRepository.save(employees);
            } catch (Exception exception) {
                log.error("failed to insert employee, Error :  " + exception);
            }
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BaseResponse.builder()
                        .message(ConstantMess.KEY_SUCCESS_CREATE)
                        .data(request).build());
    }

    @Override
    public ResponseEntity<Object> updateEmployee(EmployeeDtoRequest request) {
        log.info("begin update employee, with param {}", request);

        if (request != null) {
            if (request.getId() == null) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BaseResponse.builder().message(ConstantMess.KEY_INVALID_REQUEST).data(request).build());
            }

            try {
                Employee employees = modelMapper.map(request, Employee.class);

                employeeRepository.save(employees);
            } catch (Exception exception) {
                log.error("failed to update employee, Error :  " + exception);
            }
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BaseResponse.builder().message(ConstantMess.KEY_SUCCESS_UPDATE).data(request).build());
    }

    @Override
    public ResponseEntity<Object> deleteEmployee(Long request) {
        log.info("begin delete employee, with param {}", request);

        try {
            Optional<Employee> employee = employeeRepository.findById(request);
            if (employee.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BaseResponse.builder().message(ConstantMess.KEY_DATA_NOT_FOUND).data(request).build());
            }

            employeeRepository.delete(employee.get());
        } catch (Exception exception) {
            log.error("failed to delete employee, Error :  " + exception);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BaseResponse.builder().message(ConstantMess.KEY_SUCCESS_DELETE).data(request).build());
    }

    @Override
    public ResponseEntity<Object> listEmployee() {
        log.info("begin findAll employee");

        List<EmployeeDtoResponse> employeeDtoResponseList = new ArrayList<>();
        try {
            List<Employee> employeesList = employeeRepository.findAll();
            if (employeesList.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BaseResponse.builder().message(ConstantMess.KEY_DATA_NOT_FOUND).data(null).build());
            }

            for (Employee employee : employeesList) {
                EmployeeDtoResponse employeeDtoResponse = new EmployeeDtoResponse(employee);
                employeeDtoResponseList.add(employeeDtoResponse);
            }

        } catch (Exception exception) {
            log.error("Failed findAll Employee, error : " + exception);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BaseResponse.builder().message(ConstantMess.KEY_SUCCESS).data(employeeDtoResponseList).build());
    }
}
