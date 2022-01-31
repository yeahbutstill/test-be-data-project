package id.co.cimbniaga.octomobile.project.repository;

import id.co.cimbniaga.octomobile.project.domain.dao.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    Project findByProjectCode(String projectCode);

}
