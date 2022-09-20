package cl.tingeso.mueblesstgo.repositories;

import cl.tingeso.mueblesstgo.entities.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {

    public EmployeeEntity findByRut(String rut);

    @Query(value = "select * from employee as e where e.name = :name", nativeQuery = true)
    EmployeeEntity findByNameNativeQuery(@Param("name") String name);

}
