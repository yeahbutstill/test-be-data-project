package id.co.cimbniaga.octomobile.project.domain.dto.external;

import com.fasterxml.jackson.annotation.JsonInclude;
import id.co.cimbniaga.octomobile.project.domain.dao.Employee;
import id.co.cimbniaga.octomobile.project.domain.dao.Project;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeDtoResponse {
    private Long id;
    private String nik;
    private String firstName;
    private String lastName;
    private String gender;
    private Long salary;
    private String title;
    private List<ProjectDtoResponse> memberProject;
    private List<ProjectDtoResponse> leadProject;

    public EmployeeDtoResponse(Employee employee) {
        this.id = employee.getId();
        this.nik = employee.getNik();
        this.firstName = employee.getFirstName();
        this.lastName = employee.getLastName();
        this.gender = employee.getGender();
        this.salary = employee.getSalary();
        this.title = employee.getTitle();

        if (!employee.getMemberProject().isEmpty()) {
            memberProject = new ArrayList<>();
            for (Project project : employee.getMemberProject()) {
                ProjectDtoResponse projectDtoResponse = new ProjectDtoResponse();
                projectDtoResponse.setId(project.getId());
                projectDtoResponse.setProjectCode(project.getProjectCode());
                projectDtoResponse.setProjectDescription(project.getProjectDescription());
                projectDtoResponse.setMandays(project.getMandays());
                memberProject.add(projectDtoResponse);
            }
        }

        if (!employee.getLeadProject().isEmpty()) {
            leadProject = new ArrayList<>();
            for (Project project : employee.getLeadProject()) {
                ProjectDtoResponse projectDtoResponse = new ProjectDtoResponse();
                projectDtoResponse.setId(project.getId());
                projectDtoResponse.setProjectCode(project.getProjectCode());
                projectDtoResponse.setProjectDescription(project.getProjectDescription());
                projectDtoResponse.setMandays(project.getMandays());
                leadProject.add(projectDtoResponse);
            }
        }
    }
}
