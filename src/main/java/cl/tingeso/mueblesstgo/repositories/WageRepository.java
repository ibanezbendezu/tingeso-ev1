package cl.tingeso.mueblesstgo.repositories;

import cl.tingeso.mueblesstgo.entities.WageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface WageRepository extends JpaRepository<WageEntity, Long> {
}
