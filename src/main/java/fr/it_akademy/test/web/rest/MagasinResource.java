package fr.it_akademy.test.web.rest;

import fr.it_akademy.test.repository.MagasinRepository;
import fr.it_akademy.test.service.MagasinService;
import fr.it_akademy.test.service.dto.MagasinDTO;
import fr.it_akademy.test.web.rest.errors.BadRequestAlertException;
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
 * REST controller for managing {@link fr.it_akademy.test.domain.Magasin}.
 */
@RestController
@RequestMapping("/api/magasins")
public class MagasinResource {

    private final Logger log = LoggerFactory.getLogger(MagasinResource.class);

    private static final String ENTITY_NAME = "magasin";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MagasinService magasinService;

    private final MagasinRepository magasinRepository;

    public MagasinResource(MagasinService magasinService, MagasinRepository magasinRepository) {
        this.magasinService = magasinService;
        this.magasinRepository = magasinRepository;
    }

    /**
     * {@code POST  /magasins} : Create a new magasin.
     *
     * @param magasinDTO the magasinDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new magasinDTO, or with status {@code 400 (Bad Request)} if the magasin has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<MagasinDTO> createMagasin(@RequestBody MagasinDTO magasinDTO) throws URISyntaxException {
        log.debug("REST request to save Magasin : {}", magasinDTO);
        if (magasinDTO.getId() != null) {
            throw new BadRequestAlertException("A new magasin cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MagasinDTO result = magasinService.save(magasinDTO);
        return ResponseEntity
            .created(new URI("/api/magasins/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /magasins/:id} : Updates an existing magasin.
     *
     * @param id the id of the magasinDTO to save.
     * @param magasinDTO the magasinDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated magasinDTO,
     * or with status {@code 400 (Bad Request)} if the magasinDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the magasinDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MagasinDTO> updateMagasin(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MagasinDTO magasinDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Magasin : {}, {}", id, magasinDTO);
        if (magasinDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, magasinDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!magasinRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MagasinDTO result = magasinService.update(magasinDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, magasinDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /magasins/:id} : Partial updates given fields of an existing magasin, field will ignore if it is null
     *
     * @param id the id of the magasinDTO to save.
     * @param magasinDTO the magasinDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated magasinDTO,
     * or with status {@code 400 (Bad Request)} if the magasinDTO is not valid,
     * or with status {@code 404 (Not Found)} if the magasinDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the magasinDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MagasinDTO> partialUpdateMagasin(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MagasinDTO magasinDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Magasin partially : {}, {}", id, magasinDTO);
        if (magasinDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, magasinDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!magasinRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MagasinDTO> result = magasinService.partialUpdate(magasinDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, magasinDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /magasins} : get all the magasins.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of magasins in body.
     */
    @GetMapping("")
    public List<MagasinDTO> getAllMagasins() {
        log.debug("REST request to get all Magasins");
        return magasinService.findAll();
    }

    /**
     * {@code GET  /magasins/:id} : get the "id" magasin.
     *
     * @param id the id of the magasinDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the magasinDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MagasinDTO> getMagasin(@PathVariable Long id) {
        log.debug("REST request to get Magasin : {}", id);
        Optional<MagasinDTO> magasinDTO = magasinService.findOne(id);
        return ResponseUtil.wrapOrNotFound(magasinDTO);
    }

    /**
     * {@code DELETE  /magasins/:id} : delete the "id" magasin.
     *
     * @param id the id of the magasinDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMagasin(@PathVariable Long id) {
        log.debug("REST request to delete Magasin : {}", id);
        magasinService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
