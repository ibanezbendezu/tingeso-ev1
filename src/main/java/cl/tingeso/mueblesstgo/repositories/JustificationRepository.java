package cl.tingeso.mueblesstgo.repositories;

import cl.tingeso.mueblesstgo.entities.JustificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JustificationRepository extends JpaRepository<JustificationEntity, Long>{
}
