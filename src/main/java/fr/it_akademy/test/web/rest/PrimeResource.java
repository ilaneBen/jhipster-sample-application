package fr.it_akademy.test.web.rest;

import fr.it_akademy.test.repository.PrimeRepository;
import fr.it_akademy.test.service.PrimeService;
import fr.it_akademy.test.service.dto.PrimeDTO;
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
 * REST controller for managing {@link fr.it_akademy.test.domain.Prime}.
 */
@RestController
@RequestMapping("/api/primes")
public class PrimeResource {

    private final Logger log = LoggerFactory.getLogger(PrimeResource.class);

    private static final String ENTITY_NAME = "prime";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PrimeService primeService;

    private final PrimeRepository primeRepository;

    public PrimeResource(PrimeService primeService, PrimeRepository primeRepository) {
        this.primeService = primeService;
        this.primeRepository = primeRepository;
    }

    /**
     * {@code POST  /primes} : Create a new prime.
     *
     * @param primeDTO the primeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new primeDTO, or with status {@code 400 (Bad Request)} if the prime has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PrimeDTO> createPrime(@RequestBody PrimeDTO primeDTO) throws URISyntaxException {
        log.debug("REST request to save Prime : {}", primeDTO);
        if (primeDTO.getId() != null) {
            throw new BadRequestAlertException("A new prime cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PrimeDTO result = primeService.save(primeDTO);
        return ResponseEntity
            .created(new URI("/api/primes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /primes/:id} : Updates an existing prime.
     *
     * @param id the id of the primeDTO to save.
     * @param primeDTO the primeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated primeDTO,
     * or with status {@code 400 (Bad Request)} if the primeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the primeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PrimeDTO> updatePrime(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PrimeDTO primeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Prime : {}, {}", id, primeDTO);
        if (primeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, primeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!primeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PrimeDTO result = primeService.update(primeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, primeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /primes/:id} : Partial updates given fields of an existing prime, field will ignore if it is null
     *
     * @param id the id of the primeDTO to save.
     * @param primeDTO the primeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated primeDTO,
     * or with status {@code 400 (Bad Request)} if the primeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the primeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the primeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PrimeDTO> partialUpdatePrime(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PrimeDTO primeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Prime partially : {}, {}", id, primeDTO);
        if (primeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, primeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!primeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PrimeDTO> result = primeService.partialUpdate(primeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, primeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /primes} : get all the primes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of primes in body.
     */
    @GetMapping("")
    public List<PrimeDTO> getAllPrimes() {
        log.debug("REST request to get all Primes");
        return primeService.findAll();
    }

    /**
     * {@code GET  /primes/:id} : get the "id" prime.
     *
     * @param id the id of the primeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the primeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PrimeDTO> getPrime(@PathVariable Long id) {
        log.debug("REST request to get Prime : {}", id);
        Optional<PrimeDTO> primeDTO = primeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(primeDTO);
    }

    /**
     * {@code DELETE  /primes/:id} : delete the "id" prime.
     *
     * @param id the id of the primeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrime(@PathVariable Long id) {
        log.debug("REST request to delete Prime : {}", id);
        primeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
