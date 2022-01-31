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
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull @NotEmpty private long id;

    @Column(length = 20)
    @NonNull @NotEmpty private String nik;
    @NonNull @NotEmpty private String firstName;
    @NonNull @NotEmpty private String lastName;

    @Column(length = 15)
    @NonNull @NotEmpty private String gender;

    @Column(length = 15)
    @NonNull @NotEmpty private long salary;

    @Column(length = 30)
    @NonNull @NotEmpty private String title;

    @ManyToMany()
    @JoinTable(name = "member_project",
            joinColumns = @JoinColumn(name = "employee_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "project_id", referencedColumnName = "id"))
    private List<Project> memberProject = new ArrayList<>();

    @OneToMany(mappedBy = "leadEmployee")
    private List<Project> leadProject = new ArrayList<>();

}
