package fr.it_akademy.test.repository;

import fr.it_akademy.test.domain.Prime;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Prime entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrimeRepository extends JpaRepository<Prime, Long> {}
