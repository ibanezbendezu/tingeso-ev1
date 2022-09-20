package cl.tingeso.mueblesstgo.repositories;

import cl.tingeso.mueblesstgo.entities.WageDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WageDetailRepository extends JpaRepository<WageDetailEntity, Long> {
}
