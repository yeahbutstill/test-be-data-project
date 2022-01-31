package id.co.cimbniaga.octomobile.controller;

import id.co.cimbniaga.octomobile.project.constant.Constant;
import id.co.cimbniaga.octomobile.project.controller.EmployeeController;
import id.co.cimbniaga.octomobile.project.domain.dao.Employee;
import id.co.cimbniaga.octomobile.project.domain.dto.common.BaseResponse;
import id.co.cimbniaga.octomobile.project.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@WebMvcTest(value = EmployeeController.class)
@RunWith(SpringRunner.class)
public class EmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private EmployeeService employeeService;

    @Mock
    private ModelMapper modelMapper;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    public Employee getEmployee() {
        return Employee.builder()
                .id(1l)
                .firstName("Dede")
                .lastName("Hamzah")
                .nik("AB1234")
                .gender("laki-laki")
                .salary(1000000l)
                .title("Programmer")
                .build();
    }

    @Test
    public void detailEmployee_expectSuccess() throws Exception {
        Mockito.when(employeeService.getEmployee(any()))
                .thenReturn(ResponseEntity.ok().body(BaseResponse.builder().message(Constant.KEY_SUCCESS).data(getEmployee()).build()));

        mockMvc.perform(post("/employee/detail")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nik\":\"AB1234\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is(Constant.KEY_SUCCESS)))
                .andExpect(jsonPath("$.data.firstName", is("Dede")))
                .andExpect(jsonPath("$.data.nik", is("AB1234")));
    }

    @Test
    public void detailEmployee_expectError() throws Exception {
        Mockito.when(employeeService.getEmployee(any()))
                .thenReturn(ResponseEntity.status(HttpStatus.NOT_FOUND).body(BaseResponse.builder().message(Constant.KEY_DATA_NOT_FOUND).data(null).build()));

        mockMvc.perform(post("/employee/detail")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nik\":\"AB1234\"}"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is(Constant.KEY_DATA_NOT_FOUND)));
    }

    @Test
    public void findAllEmployee_expectSuccess() throws Exception {
        List<Employee> employeeList = Arrays.asList(getEmployee(), getEmployee());

        Mockito.when(employeeService.listEmployee()).thenReturn(ResponseEntity.ok().body(BaseResponse.builder().message(Constant.KEY_SUCCESS).data(employeeList).build()));
        mockMvc.perform(post("/employee/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is(Constant.KEY_SUCCESS)))
                .andExpect(jsonPath("$.data.[0].firstName", is("Dede")))
                .andExpect(jsonPath("$.data.[0].nik", is("AB1234")));
    }

    @Test
    public void findAllEmployee_expectError() throws Exception {
        Mockito.when(employeeService.listEmployee()).thenReturn(ResponseEntity.status(HttpStatus.NOT_FOUND).body(BaseResponse.builder().message(Constant.KEY_DATA_NOT_FOUND).data(new ArrayList<>()).build()));
        mockMvc.perform(post("/employee/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is(Constant.KEY_DATA_NOT_FOUND)));
    }

    @Test
    public void updateEmployee_expectSuccess() throws Exception {
        Mockito.when(employeeService.updateEmployee(any()))
                .thenReturn(ResponseEntity.ok().body(BaseResponse.builder().message(Constant.KEY_SUCCESS_UPDATE).data(getEmployee()).build()));

        mockMvc.perform(post("/employee/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":4,\"nik\":\"AB1234\",\"first_name\":\"Dede\",\"last_name\":\"Hamzah\",\"gender\":\"laki-laki\",\"salary\":1000000,\"title\":\"Programmer\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is(Constant.KEY_SUCCESS_UPDATE)));
    }

    @Test
    public void createEmployee_expectSuccess() throws Exception {
        Mockito.when(employeeService.insertEmployee(any()))
                .thenReturn(ResponseEntity.ok().body(BaseResponse.builder().message(Constant.KEY_SUCCESS_INSERT).data(getEmployee()).build()));

        mockMvc.perform(post("/employee/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nik\":\"AB1234\",\"first_name\":\"Dede\",\"last_name\":\"Hamzah\",\"gender\":\"laki-laki\",\"salary\":1000000,\"title\":\"Programmer\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is(Constant.KEY_SUCCESS_INSERT)));
    }

    @Test
    public void deleteEmployee_expectSuccess() throws Exception {
        Mockito.when(employeeService.deleteEmployee(any()))
                .thenReturn(ResponseEntity.ok().body(BaseResponse.builder().message(Constant.KEY_SUCCESS_DELETE).data(1l).build()));

        mockMvc.perform(post("/employee/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is(Constant.KEY_SUCCESS_DELETE)));
    }

    @Test
    public void deleteEmployee_expectError() throws Exception {
        Mockito.when(employeeService.deleteEmployee(any()))
                .thenReturn(ResponseEntity.status(HttpStatus.NOT_FOUND).body(BaseResponse.builder().message(Constant.KEY_DATA_NOT_FOUND).data(1l).build()));

        mockMvc.perform(post("/employee/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":3}"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is(Constant.KEY_DATA_NOT_FOUND)));
    }

    @Test
    public void createEmployee_expectError() throws Exception {
        Mockito.when(employeeService.insertEmployee(any()))
                .thenReturn(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseResponse.builder().message(Constant.KEY_INVALID_REQUEST).data(null).build()));

        mockMvc.perform(post("/employee/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"nik\":\"AB1234\",\"first_name\":\"Dede\",\"last_name\":\"Hamzah\",\"gender\":\"laki-laki\",\"salary\":1000000,\"title\":\"Programmer\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is(Constant.KEY_INVALID_REQUEST)));
    }

    @Test
    public void updateEmployee_expectError() throws Exception {
        Mockito.when(employeeService.updateEmployee(any()))
                .thenReturn(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseResponse.builder().message(Constant.KEY_INVALID_REQUEST).data(null).build()));

        mockMvc.perform(post("/employee/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nik\":\"AB1234\",\"first_name\":\"Dede\",\"last_name\":\"Hamzah\",\"gender\":\"laki-laki\",\"salary\":1000000,\"title\":\"Programmer\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is(Constant.KEY_INVALID_REQUEST)));
    }
}
