package fr.it_akademy.test.service;

import fr.it_akademy.test.service.dto.MagasinDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link fr.it_akademy.test.domain.Magasin}.
 */
public interface MagasinService {
    /**
     * Save a magasin.
     *
     * @param magasinDTO the entity to save.
     * @return the persisted entity.
     */
    MagasinDTO save(MagasinDTO magasinDTO);

    /**
     * Updates a magasin.
     *
     * @param magasinDTO the entity to update.
     * @return the persisted entity.
     */
    MagasinDTO update(MagasinDTO magasinDTO);

    /**
     * Partially updates a magasin.
     *
     * @param magasinDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MagasinDTO> partialUpdate(MagasinDTO magasinDTO);

    /**
     * Get all the magasins.
     *
     * @return the list of entities.
     */
    List<MagasinDTO> findAll();

    /**
     * Get the "id" magasin.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MagasinDTO> findOne(Long id);

    /**
     * Delete the "id" magasin.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
