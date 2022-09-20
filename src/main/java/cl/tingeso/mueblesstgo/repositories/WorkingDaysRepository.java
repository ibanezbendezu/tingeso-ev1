package cl.tingeso.mueblesstgo.repositories;

import cl.tingeso.mueblesstgo.entities.WorkingDaysEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Month;

@Repository
public interface WorkingDaysRepository extends JpaRepository<WorkingDaysEntity, Long> {
    public WorkingDaysEntity findByMonth(int month);
}
