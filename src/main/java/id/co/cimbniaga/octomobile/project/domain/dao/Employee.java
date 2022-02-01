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
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull
    @NotEmpty
    private Long id;

    @Column(length = 20)
    @NonNull
    @NotEmpty
    private String nik;

    @NonNull
    @NotEmpty
    private String firstName;
    @NonNull
    @NotEmpty
    private String lastName;

    @Column(length = 15)
    @NonNull
    @NotEmpty
    private String gender;

    @Column(length = 15)
    @NonNull
    @NotEmpty
    private Long salary;

    @Column(length = 30)
    @NonNull
    @NotEmpty
    private String title;

    @ManyToMany()
    @JoinTable(name = "member_project",
            joinColumns = @JoinColumn(name = "employee_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "project_id", referencedColumnName = "id"))
    @ToString.Exclude
    private List<Project> memberProject = new ArrayList<>();

    @OneToMany(mappedBy = "leadEmployee")
    @ToString.Exclude
    private List<Project> leadProject = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Employee employee = (Employee) o;
        return id != null && Objects.equals(id, employee.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
