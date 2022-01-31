package id.co.cimbniaga.octomobile.project.domain.dto.external;

import com.fasterxml.jackson.annotation.JsonInclude;
import id.co.cimbniaga.octomobile.project.domain.dao.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProjectDtoRequest {
    private Long id;
    private String projectCode;
    private String projectDescription;
    private long mandays;
    private Employee leadEmployee;
}
