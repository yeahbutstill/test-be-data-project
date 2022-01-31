package id.co.cimbniaga.octomobile.project.service.implementation;

import id.co.cimbniaga.octomobile.project.constant.ConstantMess;
import id.co.cimbniaga.octomobile.project.domain.dao.Project;
import id.co.cimbniaga.octomobile.project.domain.dto.common.BaseResponse;
import id.co.cimbniaga.octomobile.project.domain.dto.external.ProjectDtoRequest;
import id.co.cimbniaga.octomobile.project.domain.dto.external.ProjectDtoResponse;
import id.co.cimbniaga.octomobile.project.repository.ProjectRepository;
import id.co.cimbniaga.octomobile.project.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ProjectServiceImpl implements ProjectService {

    public final ModelMapper modelMapper;
    private final ProjectRepository projectRepository;

    @Override
    public ResponseEntity<Object> getProject(String request) {
        log.info("begin get project, with codeProject {}", request);
        ProjectDtoResponse projectDtoResponse = null;
        try {
            Optional<Project> project = Optional.ofNullable(projectRepository.findByProjectCode(request));
            if (project.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BaseResponse.builder().message(ConstantMess.KEY_DATA_NOT_FOUND).data(null).build());
            }

            projectDtoResponse = new ProjectDtoResponse(project.get());
        } catch (Exception exception) {
            log.error("failed get project with projectCode, Error :  " + exception);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BaseResponse.builder().message(ConstantMess.KEY_SUCCESS).data(projectDtoResponse).build());
    }

    @Override
    public ResponseEntity<Object> insertProject(ProjectDtoRequest request) {
        log.info("begin insert project, with param {}", request);

        Project project;
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
                project = modelMapper.map(request, Project.class);

                projectRepository.save(project);
            } catch (Exception exception) {
                log.error("failed to insert project, Error :  " + exception);
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
    public ResponseEntity<Object> updateProject(ProjectDtoRequest request) {
        log.info("begin update project, with param {}", request);

        Project project;
        if (request != null) {
            if (request.getId() == null) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BaseResponse.builder().message(ConstantMess.KEY_INVALID_REQUEST).data(request).build());
            }

            try {
                project = modelMapper.map(request, Project.class);

                projectRepository.save(project);
            } catch (Exception exception) {
                log.error("failed to update project, Error :  " + exception);
            }
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BaseResponse.builder().message(ConstantMess.KEY_SUCCESS_UPDATE).data(request).build());
    }

    @Override
    public ResponseEntity<Object> deleteProject(Long request) {
        log.info("begin delete project, with param {}", request);

        try {
            Optional<Project> project = projectRepository.findById(request);
            if (project.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BaseResponse.builder().message(ConstantMess.KEY_DATA_NOT_FOUND).data(request).build());
            }

            projectRepository.delete(project.get());
        } catch (Exception exception) {
            log.error("failed to delete project, Error :  " + exception);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BaseResponse.builder().message(ConstantMess.KEY_SUCCESS_DELETE).data(request).build());
    }

    @Override
    public ResponseEntity<Object> listProject() {
        log.info("begin findAll project");

        List<ProjectDtoResponse> projectDtoList = new ArrayList<>();
        try {
            List<Project> projectList = projectRepository.findAll();
            if (projectList.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BaseResponse.builder().message(ConstantMess.KEY_DATA_NOT_FOUND).data(null).build());
            }

            for (Project project : projectList) {
                ProjectDtoResponse projectDtoResponse = new ProjectDtoResponse(project);
                projectDtoList.add(projectDtoResponse);
            }

        } catch (Exception exception) {
            log.error("Failed findAll project, Error : " + exception);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BaseResponse.builder().message(ConstantMess.KEY_SUCCESS).data(projectDtoList).build());
    }
}
