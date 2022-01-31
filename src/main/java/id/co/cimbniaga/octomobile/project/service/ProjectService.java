package id.co.cimbniaga.octomobile.project.service;

import id.co.cimbniaga.octomobile.project.domain.dto.external.ProjectDtoRequest;
import org.springframework.http.ResponseEntity;

public interface ProjectService {
    ResponseEntity<Object> getProject(String request);

    ResponseEntity<Object> insertProject(ProjectDtoRequest request);

    ResponseEntity<Object> updateProject(ProjectDtoRequest request);

    ResponseEntity<Object> deleteProject(Long request);

    ResponseEntity<Object> listProject();
}
