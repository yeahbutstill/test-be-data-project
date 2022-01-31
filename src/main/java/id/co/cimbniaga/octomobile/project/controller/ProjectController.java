package id.co.cimbniaga.octomobile.project.controller;

import id.co.cimbniaga.octomobile.project.domain.dto.external.ProjectDtoRequest;
import id.co.cimbniaga.octomobile.project.service.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    ProjectService projectService;

    @PostMapping(value = "/")
    public ResponseEntity<Object> findAllProject() {
        try {
            return projectService.listProject();
        } catch (Exception e) {
            log.error("Happened error when findAll project : {}", e.getClass().getName(), e.getStackTrace());
            log.trace("{}", e);
            throw e;
        }
    }

    @PostMapping(value = "/detail")
    public ResponseEntity<Object> getProject(@RequestBody ProjectDtoRequest request) {
        try {
            return projectService.getProject(request.getProjectCode());
        } catch (Exception e) {
            log.error("Happened error when get project with projectCode: {}", e.getClass().getName(), e.getStackTrace());
            log.trace("{}", e);
            throw e;
        }
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Object> insertProject(@RequestBody ProjectDtoRequest request) {
        try {
            return projectService.insertProject(request);
        } catch (Exception e) {
            log.error("Happened error when insert employee : {}", e.getClass().getName(), e.getStackTrace());
            log.trace("{}", e);
            throw e;
        }
    }

    @PostMapping(value = "/delete")
    public ResponseEntity<Object> deleteProject(@RequestBody ProjectDtoRequest request) {
        try {
            return projectService.deleteProject(request.getId());
        } catch (Exception e) {
            log.error("Happened error when delete project : {}", e.getClass().getName(), e.getStackTrace());
            log.trace("{}", e);
            throw e;
        }
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Object> updateEmployee(@RequestBody ProjectDtoRequest request) {
        try {
            return projectService.updateProject(request);
        } catch (Exception e) {
            log.error("Happened error when update project : {}", e.getClass().getName(), e.getStackTrace());
            log.trace("{}", e);
            throw e;
        }
    }
}
