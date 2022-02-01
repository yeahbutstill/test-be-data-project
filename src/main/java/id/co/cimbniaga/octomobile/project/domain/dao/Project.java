package id.co.cimbniaga.octomobile.project.domain.dao;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
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
    @ToString.Exclude
    private List<Employee> memberEmployee = new ArrayList<>();

    @OneToOne()
    @JoinTable(name = "lead_project",
            joinColumns = @JoinColumn(name = "project_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id", referencedColumnName = "id"))
    private Employee leadEmployee;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Project project = (Project) o;
        return id != null && Objects.equals(id, project.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
