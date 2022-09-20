package cl.tingeso.mueblesstgo.repositories;

import cl.tingeso.mueblesstgo.entities.WorkedDayEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkedDayRepository extends JpaRepository<WorkedDayEntity, Long> {
}
