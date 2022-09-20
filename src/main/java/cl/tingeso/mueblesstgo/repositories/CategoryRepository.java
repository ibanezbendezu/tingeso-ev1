package cl.tingeso.mueblesstgo.repositories;

import cl.tingeso.mueblesstgo.entities.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    public CategoryEntity findByType(Character type);
}
