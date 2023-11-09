package fr.it_akademy.test.web.rest;

import fr.it_akademy.test.repository.VendeurRepository;
import fr.it_akademy.test.service.VendeurService;
import fr.it_akademy.test.service.dto.VendeurDTO;
import fr.it_akademy.test.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link fr.it_akademy.test.domain.Vendeur}.
 */
@RestController
@RequestMapping("/api/vendeurs")
public class VendeurResource {

    private final Logger log = LoggerFactory.getLogger(VendeurResource.class);

    private static final String ENTITY_NAME = "vendeur";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VendeurService vendeurService;

    private final VendeurRepository vendeurRepository;

    public VendeurResource(VendeurService vendeurService, VendeurRepository vendeurRepository) {
        this.vendeurService = vendeurService;
        this.vendeurRepository = vendeurRepository;
    }

    /**
     * {@code POST  /vendeurs} : Create a new vendeur.
     *
     * @param vendeurDTO the vendeurDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vendeurDTO, or with status {@code 400 (Bad Request)} if the vendeur has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<VendeurDTO> createVendeur(@Valid @RequestBody VendeurDTO vendeurDTO) throws URISyntaxException {
        log.debug("REST request to save Vendeur : {}", vendeurDTO);
        if (vendeurDTO.getId() != null) {
            throw new BadRequestAlertException("A new vendeur cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VendeurDTO result = vendeurService.save(vendeurDTO);
        return ResponseEntity
            .created(new URI("/api/vendeurs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vendeurs/:id} : Updates an existing vendeur.
     *
     * @param id the id of the vendeurDTO to save.
     * @param vendeurDTO the vendeurDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vendeurDTO,
     * or with status {@code 400 (Bad Request)} if the vendeurDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vendeurDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<VendeurDTO> updateVendeur(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody VendeurDTO vendeurDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Vendeur : {}, {}", id, vendeurDTO);
        if (vendeurDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vendeurDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vendeurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        VendeurDTO result = vendeurService.update(vendeurDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vendeurDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /vendeurs/:id} : Partial updates given fields of an existing vendeur, field will ignore if it is null
     *
     * @param id the id of the vendeurDTO to save.
     * @param vendeurDTO the vendeurDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vendeurDTO,
     * or with status {@code 400 (Bad Request)} if the vendeurDTO is not valid,
     * or with status {@code 404 (Not Found)} if the vendeurDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the vendeurDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<VendeurDTO> partialUpdateVendeur(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody VendeurDTO vendeurDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Vendeur partially : {}, {}", id, vendeurDTO);
        if (vendeurDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vendeurDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vendeurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<VendeurDTO> result = vendeurService.partialUpdate(vendeurDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vendeurDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /vendeurs} : get all the vendeurs.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vendeurs in body.
     */
    @GetMapping("")
    public List<VendeurDTO> getAllVendeurs(@RequestParam(required = false, defaultValue = "true") boolean eagerload) {
        log.debug("REST request to get all Vendeurs");
        return vendeurService.findAll();
    }

    /**
     * {@code GET  /vendeurs/:id} : get the "id" vendeur.
     *
     * @param id the id of the vendeurDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vendeurDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<VendeurDTO> getVendeur(@PathVariable Long id) {
        log.debug("REST request to get Vendeur : {}", id);
        Optional<VendeurDTO> vendeurDTO = vendeurService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vendeurDTO);
    }

    /**
     * {@code DELETE  /vendeurs/:id} : delete the "id" vendeur.
     *
     * @param id the id of the vendeurDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVendeur(@PathVariable Long id) {
        log.debug("REST request to delete Vendeur : {}", id);
        vendeurService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
