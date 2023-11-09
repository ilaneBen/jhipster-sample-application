package fr.it_akademy.test.service;

import fr.it_akademy.test.service.dto.VendeurDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link fr.it_akademy.test.domain.Vendeur}.
 */
public interface VendeurService {
    /**
     * Save a vendeur.
     *
     * @param vendeurDTO the entity to save.
     * @return the persisted entity.
     */
    VendeurDTO save(VendeurDTO vendeurDTO);

    /**
     * Updates a vendeur.
     *
     * @param vendeurDTO the entity to update.
     * @return the persisted entity.
     */
    VendeurDTO update(VendeurDTO vendeurDTO);

    /**
     * Partially updates a vendeur.
     *
     * @param vendeurDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<VendeurDTO> partialUpdate(VendeurDTO vendeurDTO);

    /**
     * Get all the vendeurs.
     *
     * @return the list of entities.
     */
    List<VendeurDTO> findAll();

    /**
     * Get all the vendeurs with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<VendeurDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" vendeur.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<VendeurDTO> findOne(Long id);

    /**
     * Delete the "id" vendeur.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
