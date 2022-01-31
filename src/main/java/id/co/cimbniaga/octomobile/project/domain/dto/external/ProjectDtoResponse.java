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
public class ProjectDtoResponse {
    private Long id;
    private String projectCode;
    private String projectDescription;
    private Long mandays;
    private List<EmployeeDtoResponse> memberEmployee;
    private EmployeeDtoResponse leadEmployee;

    public ProjectDtoResponse(Project project) {
        this.id = project.getId();
        this.projectCode = project.getProjectCode();
        this.projectDescription = project.getProjectDescription();
        this.mandays = project.getMandays();

        if (!project.getMemberEmployee().isEmpty()) {
            memberEmployee = new ArrayList<>();
            for (Employee employee : project.getMemberEmployee()) {
                EmployeeDtoResponse employeeDtoResponse = new EmployeeDtoResponse();
                employeeDtoResponse.setId(employee.getId());
                employeeDtoResponse.setNik(employee.getNik());
                employeeDtoResponse.setFirstName(employee.getFirstName());
                employeeDtoResponse.setLastName(employee.getLastName());
                employeeDtoResponse.setSalary(employee.getSalary());
                employeeDtoResponse.setGender(employee.getGender());
                employeeDtoResponse.setTitle(employee.getTitle());
                memberEmployee.add(employeeDtoResponse);
            }
        }

        if (project.getLeadEmployee() != null) {
            leadEmployee = new EmployeeDtoResponse();
            Employee employee = project.getLeadEmployee();
            leadEmployee.setId(employee.getId());
            leadEmployee.setNik(employee.getNik());
            leadEmployee.setFirstName(employee.getFirstName());
            leadEmployee.setLastName(employee.getLastName());
            leadEmployee.setSalary(employee.getSalary());
            leadEmployee.setGender(employee.getGender());
            leadEmployee.setTitle(employee.getTitle());
        }
    }
}
