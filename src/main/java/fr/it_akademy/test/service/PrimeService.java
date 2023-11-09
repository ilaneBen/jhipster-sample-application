package fr.it_akademy.test.service;

import fr.it_akademy.test.service.dto.PrimeDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link fr.it_akademy.test.domain.Prime}.
 */
public interface PrimeService {
    /**
     * Save a prime.
     *
     * @param primeDTO the entity to save.
     * @return the persisted entity.
     */
    PrimeDTO save(PrimeDTO primeDTO);

    /**
     * Updates a prime.
     *
     * @param primeDTO the entity to update.
     * @return the persisted entity.
     */
    PrimeDTO update(PrimeDTO primeDTO);

    /**
     * Partially updates a prime.
     *
     * @param primeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PrimeDTO> partialUpdate(PrimeDTO primeDTO);

    /**
     * Get all the primes.
     *
     * @return the list of entities.
     */
    List<PrimeDTO> findAll();

    /**
     * Get the "id" prime.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PrimeDTO> findOne(Long id);

    /**
     * Delete the "id" prime.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
