package id.co.cimbniaga.octomobile.service;

import id.co.cimbniaga.octomobile.project.domain.dao.Project;
import id.co.cimbniaga.octomobile.project.domain.dto.external.ProjectDtoRequest;
import id.co.cimbniaga.octomobile.project.repository.ProjectRepository;
import id.co.cimbniaga.octomobile.project.service.impl.ProjectServiceImpl;
import id.co.cimbniaga.octomobile.project.service.implementation.ProjectServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@Slf4j
@RunWith(SpringRunner.class)
public class ProjectServiceTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectServiceImpl projectService;

    @Mock
    private ModelMapper modelMapper;

    public Project getProject() {
        return Project.builder()
                .id(1L)
                .projectCode("Code01")
                .projectDescription("Project Transaction")
                .mandays(10l)
                .build();
    }

    @Test
    public void getListProject_expectSuccess() {
        List<Project> projectList = List.of(getProject());
        Mockito.when(projectRepository.findAll()).thenReturn(projectList);
        ResponseEntity<Object> response = projectService.listProject();
        Assertions.assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void getProject_expectSuccess() {
        Mockito.when(projectRepository.findByProjectCode(any())).thenReturn(getProject());
        ResponseEntity<Object> response = projectService.getProject(any());
        Assertions.assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void insertProject_expectSuccess() {
        ResponseEntity<Object> response = projectService.insertProject(ProjectDtoRequest.builder()
                .projectCode("Code01")
                .projectDescription("Project Transaction")
                .mandays(10l)
                .build());
        Assertions.assertEquals(200, response.getStatusCodeValue());
        Mockito.verify(projectRepository).save(any());
    }

    @Test
    public void updateProject_expectSuccess() {
        ResponseEntity<Object> response = projectService.updateProject(ProjectDtoRequest.builder()
                .id(1L)
                .projectCode("Code01")
                .projectDescription("Project Transaction")
                .mandays(10l)
                .build());
        Assertions.assertEquals(200, response.getStatusCodeValue());
        Mockito.verify(projectRepository).save(any());
    }

    @Test
    public void deleteProject_expectSuccess() {
        Mockito.when(projectRepository.findById(any())).thenReturn(Optional.of(getProject()));
        ResponseEntity<Object> response = projectService.deleteProject(getProject().getId());
        Assertions.assertEquals(200, response.getStatusCodeValue());
        Mockito.verify(projectRepository).delete(any());
    }

    @Test
    public void deleteProject_expectError() {
        Mockito.when(projectRepository.findById(any())).thenReturn(Optional.empty());
        ResponseEntity<Object> response = projectService.deleteProject(getProject().getId());
        Assertions.assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void getListProject_expectError() {
        Mockito.when(projectRepository.findAll()).thenReturn(new ArrayList<>());
        ResponseEntity<Object> response = projectService.listProject();
        Assertions.assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void updateProject_expectError() {
        ResponseEntity<Object> response = projectService.updateProject(ProjectDtoRequest.builder()
                .projectCode("Code01")
                .projectDescription("Project Transaction")
                .mandays(10l)
                .build());
        Assertions.assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    public void insertProject_expectError() {
        ResponseEntity<Object> response = projectService.insertProject(ProjectDtoRequest.builder()
                .id(1l)
                .projectCode("Code01")
                .projectDescription("Project Transaction")
                .mandays(10l)
                .build());
        Assertions.assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    public void getProject_expectError() {
        Mockito.when(projectRepository.findByProjectCode(any())).thenReturn(null);
        ResponseEntity<Object> response = projectService.getProject(any());
        Assertions.assertEquals(404, response.getStatusCodeValue());
    }
}
