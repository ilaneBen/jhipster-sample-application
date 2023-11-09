package fr.it_akademy.test.repository;

import fr.it_akademy.test.domain.Produits;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Produits entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProduitsRepository extends JpaRepository<Produits, Long> {}
