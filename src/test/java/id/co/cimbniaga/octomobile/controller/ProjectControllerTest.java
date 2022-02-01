package id.co.cimbniaga.octomobile.controller;

import id.co.cimbniaga.octomobile.project.constant.ConstantMess;
import id.co.cimbniaga.octomobile.project.domain.dao.Project;
import id.co.cimbniaga.octomobile.project.domain.dto.common.BaseResponse;
import id.co.cimbniaga.octomobile.project.service.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class ProjectControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private ProjectService projectService;

    @Mock
    private ModelMapper modelMapper;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    public Project getProject() {
        return Project.builder()
                .id(1L)
                .projectCode("Code01")
                .projectDescription("Project Transaction")
                .mandays(10L)
                .build();
    }

    @Test
    public void findAllProject_expectSuccess() throws Exception {
        List<Project> projectList = Arrays.asList(getProject(), getProject());

        Mockito.when(projectService.listProject()).thenReturn(ResponseEntity.ok().body(BaseResponse.builder().message(ConstantMess.KEY_SUCCESS).data(projectList).build()));
        mockMvc.perform(post("/project/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is(ConstantMess.KEY_SUCCESS)))
                .andExpect(jsonPath("$.data.[0].projectCode", is("Code01")))
                .andExpect(jsonPath("$.data.[0].projectDescription", is("Project Transaction")));
    }

    @Test
    public void findAllProject_expectError() throws Exception {
        Mockito.when(projectService.listProject()).thenReturn(ResponseEntity.status(HttpStatus.NOT_FOUND).body(BaseResponse.builder().message(ConstantMess.KEY_DATA_NOT_FOUND).data(new ArrayList<>()).build()));
        mockMvc.perform(post("/project/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is(ConstantMess.KEY_DATA_NOT_FOUND)));

    }

    @Test
    public void detailProject_expectSuccess() throws Exception {
        Mockito.when(projectService.getProject(any()))
                .thenReturn(ResponseEntity.ok().body(BaseResponse.builder().message(ConstantMess.KEY_SUCCESS).data(getProject()).build()));

        mockMvc.perform(post("/project/detail")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"project_code\":\"Code01\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is(ConstantMess.KEY_SUCCESS)))
                .andExpect(jsonPath("$.data.projectCode", is("Code01")))
                .andExpect(jsonPath("$.data.projectDescription", is("Project Transaction")));
    }

    @Test
    public void detailProject_expectError() throws Exception {
        Mockito.when(projectService.getProject(any()))
                .thenReturn(ResponseEntity.status(HttpStatus.NOT_FOUND).body(BaseResponse.builder().message(ConstantMess.KEY_DATA_NOT_FOUND).data(null).build()));

        mockMvc.perform(post("/project/detail")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"project_code\":\"Code02\"}"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is(ConstantMess.KEY_DATA_NOT_FOUND)));
    }

    @Test
    public void updateProject_expectSuccess() throws Exception {
        Mockito.when(projectService.updateProject(any()))
                .thenReturn(ResponseEntity.ok().body(BaseResponse.builder().message(ConstantMess.KEY_SUCCESS_UPDATE).data(getProject()).build()));

        mockMvc.perform(post("/project/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"project_code\":\"Code01\",\"project_description\":\"Setoran\",\"mandays\":10}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is(ConstantMess.KEY_SUCCESS_UPDATE)));
    }

    @Test
    public void updateProject_expectError() throws Exception {
        Mockito.when(projectService.updateProject(any()))
                .thenReturn(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseResponse.builder().message(ConstantMess.KEY_INVALID_REQUEST).data(null).build()));

        mockMvc.perform(post("/project/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"project_code\":\"Code01\",\"project_description\":\"Setoran\",\"mandays\":10}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is(ConstantMess.KEY_INVALID_REQUEST)));
    }

    @Test
    public void createProject_expectSuccess() throws Exception {
        Mockito.when(projectService.insertProject(any()))
                .thenReturn(ResponseEntity.ok().body(BaseResponse.builder().message(ConstantMess.KEY_SUCCESS_CREATE).data(null).build()));

        mockMvc.perform(post("/project/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"project_code\":\"code125\",\"project_description\":\"TransactionSetoran\",\"mandays\":10}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is(ConstantMess.KEY_SUCCESS_CREATE)));
    }

    @Test
    public void createProject_expectError() throws Exception {
        Mockito.when(projectService.insertProject(any()))
                .thenReturn(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseResponse.builder().message(ConstantMess.KEY_INVALID_REQUEST).data(null).build()));

        mockMvc.perform(post("/project/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"project_code\":\"Code01\",\"project_description\":\"Setoran\",\"mandays\":10}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is(ConstantMess.KEY_INVALID_REQUEST)));
    }

    @Test
    public void deleteProject_expectSuccess() throws Exception {
        Mockito.when(projectService.deleteProject(any()))
                .thenReturn(ResponseEntity.ok().body(BaseResponse.builder().message(ConstantMess.KEY_SUCCESS_DELETE).data(1L).build()));

        mockMvc.perform(post("/project/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is(ConstantMess.KEY_SUCCESS_DELETE)));
    }

    @Test
    public void deleteProject_expectError() throws Exception {
        Mockito.when(projectService.deleteProject(any()))
                .thenReturn(ResponseEntity.status(HttpStatus.NOT_FOUND).body(BaseResponse.builder().message(ConstantMess.KEY_DATA_NOT_FOUND).data(null).build()));

        mockMvc.perform(post("/project/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":3}"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is(ConstantMess.KEY_DATA_NOT_FOUND)));
    }
}
