package id.co.cimbniaga.octomobile.project.repository;

import id.co.cimbniaga.octomobile.project.domain.dao.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Employee findByNik(String nik);

}
