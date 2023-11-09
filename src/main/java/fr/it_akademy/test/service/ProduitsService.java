package fr.it_akademy.test.service;

import fr.it_akademy.test.service.dto.ProduitsDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link fr.it_akademy.test.domain.Produits}.
 */
public interface ProduitsService {
    /**
     * Save a produits.
     *
     * @param produitsDTO the entity to save.
     * @return the persisted entity.
     */
    ProduitsDTO save(ProduitsDTO produitsDTO);

    /**
     * Updates a produits.
     *
     * @param produitsDTO the entity to update.
     * @return the persisted entity.
     */
    ProduitsDTO update(ProduitsDTO produitsDTO);

    /**
     * Partially updates a produits.
     *
     * @param produitsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ProduitsDTO> partialUpdate(ProduitsDTO produitsDTO);

    /**
     * Get all the produits.
     *
     * @return the list of entities.
     */
    List<ProduitsDTO> findAll();

    /**
     * Get the "id" produits.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProduitsDTO> findOne(Long id);

    /**
     * Delete the "id" produits.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
