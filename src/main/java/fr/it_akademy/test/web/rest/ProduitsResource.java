package fr.it_akademy.test.web.rest;

import fr.it_akademy.test.repository.ProduitsRepository;
import fr.it_akademy.test.service.ProduitsService;
import fr.it_akademy.test.service.dto.ProduitsDTO;
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
 * REST controller for managing {@link fr.it_akademy.test.domain.Produits}.
 */
@RestController
@RequestMapping("/api/produits")
public class ProduitsResource {

    private final Logger log = LoggerFactory.getLogger(ProduitsResource.class);

    private static final String ENTITY_NAME = "produits";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProduitsService produitsService;

    private final ProduitsRepository produitsRepository;

    public ProduitsResource(ProduitsService produitsService, ProduitsRepository produitsRepository) {
        this.produitsService = produitsService;
        this.produitsRepository = produitsRepository;
    }

    /**
     * {@code POST  /produits} : Create a new produits.
     *
     * @param produitsDTO the produitsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new produitsDTO, or with status {@code 400 (Bad Request)} if the produits has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ProduitsDTO> createProduits(@Valid @RequestBody ProduitsDTO produitsDTO) throws URISyntaxException {
        log.debug("REST request to save Produits : {}", produitsDTO);
        if (produitsDTO.getId() != null) {
            throw new BadRequestAlertException("A new produits cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProduitsDTO result = produitsService.save(produitsDTO);
        return ResponseEntity
            .created(new URI("/api/produits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /produits/:id} : Updates an existing produits.
     *
     * @param id the id of the produitsDTO to save.
     * @param produitsDTO the produitsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated produitsDTO,
     * or with status {@code 400 (Bad Request)} if the produitsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the produitsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProduitsDTO> updateProduits(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProduitsDTO produitsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Produits : {}, {}", id, produitsDTO);
        if (produitsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, produitsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!produitsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProduitsDTO result = produitsService.update(produitsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, produitsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /produits/:id} : Partial updates given fields of an existing produits, field will ignore if it is null
     *
     * @param id the id of the produitsDTO to save.
     * @param produitsDTO the produitsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated produitsDTO,
     * or with status {@code 400 (Bad Request)} if the produitsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the produitsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the produitsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProduitsDTO> partialUpdateProduits(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProduitsDTO produitsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Produits partially : {}, {}", id, produitsDTO);
        if (produitsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, produitsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!produitsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProduitsDTO> result = produitsService.partialUpdate(produitsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, produitsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /produits} : get all the produits.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of produits in body.
     */
    @GetMapping("")
    public List<ProduitsDTO> getAllProduits() {
        log.debug("REST request to get all Produits");
        return produitsService.findAll();
    }

    /**
     * {@code GET  /produits/:id} : get the "id" produits.
     *
     * @param id the id of the produitsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the produitsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProduitsDTO> getProduits(@PathVariable Long id) {
        log.debug("REST request to get Produits : {}", id);
        Optional<ProduitsDTO> produitsDTO = produitsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(produitsDTO);
    }

    /**
     * {@code DELETE  /produits/:id} : delete the "id" produits.
     *
     * @param id the id of the produitsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduits(@PathVariable Long id) {
        log.debug("REST request to delete Produits : {}", id);
        produitsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
