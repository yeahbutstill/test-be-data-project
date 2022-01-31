package id.co.cimbniaga.octomobile.project.domain.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull
    @NotEmpty
    private Long id;

    @Column(length = 20)
    @NonNull
    @NotEmpty
    private String projectCode;

    @NonNull
    @NotEmpty
    private String projectDescription;

    @Column(length = 10)
    @NonNull
    @NotEmpty
    private Long mandays;

    @ManyToMany(mappedBy = "memberProject")
    private List<Employee> memberEmployee = new ArrayList<>();

    @OneToOne()
    @JoinTable(name = "lead_project",
            joinColumns = @JoinColumn(name = "project_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id", referencedColumnName = "id"))
    private Employee leadEmployee;

}
