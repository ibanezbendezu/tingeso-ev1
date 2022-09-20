package cl.tingeso.mueblesstgo.repositories;

import cl.tingeso.mueblesstgo.entities.ApprovalEntity;
import cl.tingeso.mueblesstgo.entities.JustificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApprovalRepository extends JpaRepository<ApprovalEntity, Long>{
}
