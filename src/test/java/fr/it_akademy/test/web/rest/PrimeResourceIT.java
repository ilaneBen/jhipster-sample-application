package fr.it_akademy.test.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.it_akademy.test.IntegrationTest;
import fr.it_akademy.test.domain.Prime;
import fr.it_akademy.test.repository.PrimeRepository;
import fr.it_akademy.test.service.dto.PrimeDTO;
import fr.it_akademy.test.service.mapper.PrimeMapper;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PrimeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PrimeResourceIT {

    private static final String DEFAULT_NOM_VENDEUR = "AAAAAAAAAA";
    private static final String UPDATED_NOM_VENDEUR = "BBBBBBBBBB";

    private static final Double DEFAULT_MONTANT = 1D;
    private static final Double UPDATED_MONTANT = 2D;

    private static final String ENTITY_API_URL = "/api/primes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PrimeRepository primeRepository;

    @Autowired
    private PrimeMapper primeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPrimeMockMvc;

    private Prime prime;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Prime createEntity(EntityManager em) {
        Prime prime = new Prime().nomVendeur(DEFAULT_NOM_VENDEUR).montant(DEFAULT_MONTANT);
        return prime;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Prime createUpdatedEntity(EntityManager em) {
        Prime prime = new Prime().nomVendeur(UPDATED_NOM_VENDEUR).montant(UPDATED_MONTANT);
        return prime;
    }

    @BeforeEach
    public void initTest() {
        prime = createEntity(em);
    }

    @Test
    @Transactional
    void createPrime() throws Exception {
        int databaseSizeBeforeCreate = primeRepository.findAll().size();
        // Create the Prime
        PrimeDTO primeDTO = primeMapper.toDto(prime);
        restPrimeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(primeDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Prime in the database
        List<Prime> primeList = primeRepository.findAll();
        assertThat(primeList).hasSize(databaseSizeBeforeCreate + 1);
        Prime testPrime = primeList.get(primeList.size() - 1);
        assertThat(testPrime.getNomVendeur()).isEqualTo(DEFAULT_NOM_VENDEUR);
        assertThat(testPrime.getMontant()).isEqualTo(DEFAULT_MONTANT);
    }

    @Test
    @Transactional
    void createPrimeWithExistingId() throws Exception {
        // Create the Prime with an existing ID
        prime.setId(1L);
        PrimeDTO primeDTO = primeMapper.toDto(prime);

        int databaseSizeBeforeCreate = primeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrimeMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(primeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Prime in the database
        List<Prime> primeList = primeRepository.findAll();
        assertThat(primeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPrimes() throws Exception {
        // Initialize the database
        primeRepository.saveAndFlush(prime);

        // Get all the primeList
        restPrimeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prime.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomVendeur").value(hasItem(DEFAULT_NOM_VENDEUR)))
            .andExpect(jsonPath("$.[*].montant").value(hasItem(DEFAULT_MONTANT.doubleValue())));
    }

    @Test
    @Transactional
    void getPrime() throws Exception {
        // Initialize the database
        primeRepository.saveAndFlush(prime);

        // Get the prime
        restPrimeMockMvc
            .perform(get(ENTITY_API_URL_ID, prime.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(prime.getId().intValue()))
            .andExpect(jsonPath("$.nomVendeur").value(DEFAULT_NOM_VENDEUR))
            .andExpect(jsonPath("$.montant").value(DEFAULT_MONTANT.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingPrime() throws Exception {
        // Get the prime
        restPrimeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPrime() throws Exception {
        // Initialize the database
        primeRepository.saveAndFlush(prime);

        int databaseSizeBeforeUpdate = primeRepository.findAll().size();

        // Update the prime
        Prime updatedPrime = primeRepository.findById(prime.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPrime are not directly saved in db
        em.detach(updatedPrime);
        updatedPrime.nomVendeur(UPDATED_NOM_VENDEUR).montant(UPDATED_MONTANT);
        PrimeDTO primeDTO = primeMapper.toDto(updatedPrime);

        restPrimeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, primeDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(primeDTO))
            )
            .andExpect(status().isOk());

        // Validate the Prime in the database
        List<Prime> primeList = primeRepository.findAll();
        assertThat(primeList).hasSize(databaseSizeBeforeUpdate);
        Prime testPrime = primeList.get(primeList.size() - 1);
        assertThat(testPrime.getNomVendeur()).isEqualTo(UPDATED_NOM_VENDEUR);
        assertThat(testPrime.getMontant()).isEqualTo(UPDATED_MONTANT);
    }

    @Test
    @Transactional
    void putNonExistingPrime() throws Exception {
        int databaseSizeBeforeUpdate = primeRepository.findAll().size();
        prime.setId(longCount.incrementAndGet());

        // Create the Prime
        PrimeDTO primeDTO = primeMapper.toDto(prime);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrimeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, primeDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(primeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Prime in the database
        List<Prime> primeList = primeRepository.findAll();
        assertThat(primeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPrime() throws Exception {
        int databaseSizeBeforeUpdate = primeRepository.findAll().size();
        prime.setId(longCount.incrementAndGet());

        // Create the Prime
        PrimeDTO primeDTO = primeMapper.toDto(prime);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrimeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(primeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Prime in the database
        List<Prime> primeList = primeRepository.findAll();
        assertThat(primeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPrime() throws Exception {
        int databaseSizeBeforeUpdate = primeRepository.findAll().size();
        prime.setId(longCount.incrementAndGet());

        // Create the Prime
        PrimeDTO primeDTO = primeMapper.toDto(prime);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrimeMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(primeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Prime in the database
        List<Prime> primeList = primeRepository.findAll();
        assertThat(primeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePrimeWithPatch() throws Exception {
        // Initialize the database
        primeRepository.saveAndFlush(prime);

        int databaseSizeBeforeUpdate = primeRepository.findAll().size();

        // Update the prime using partial update
        Prime partialUpdatedPrime = new Prime();
        partialUpdatedPrime.setId(prime.getId());

        restPrimeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPrime.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPrime))
            )
            .andExpect(status().isOk());

        // Validate the Prime in the database
        List<Prime> primeList = primeRepository.findAll();
        assertThat(primeList).hasSize(databaseSizeBeforeUpdate);
        Prime testPrime = primeList.get(primeList.size() - 1);
        assertThat(testPrime.getNomVendeur()).isEqualTo(DEFAULT_NOM_VENDEUR);
        assertThat(testPrime.getMontant()).isEqualTo(DEFAULT_MONTANT);
    }

    @Test
    @Transactional
    void fullUpdatePrimeWithPatch() throws Exception {
        // Initialize the database
        primeRepository.saveAndFlush(prime);

        int databaseSizeBeforeUpdate = primeRepository.findAll().size();

        // Update the prime using partial update
        Prime partialUpdatedPrime = new Prime();
        partialUpdatedPrime.setId(prime.getId());

        partialUpdatedPrime.nomVendeur(UPDATED_NOM_VENDEUR).montant(UPDATED_MONTANT);

        restPrimeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPrime.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPrime))
            )
            .andExpect(status().isOk());

        // Validate the Prime in the database
        List<Prime> primeList = primeRepository.findAll();
        assertThat(primeList).hasSize(databaseSizeBeforeUpdate);
        Prime testPrime = primeList.get(primeList.size() - 1);
        assertThat(testPrime.getNomVendeur()).isEqualTo(UPDATED_NOM_VENDEUR);
        assertThat(testPrime.getMontant()).isEqualTo(UPDATED_MONTANT);
    }

    @Test
    @Transactional
    void patchNonExistingPrime() throws Exception {
        int databaseSizeBeforeUpdate = primeRepository.findAll().size();
        prime.setId(longCount.incrementAndGet());

        // Create the Prime
        PrimeDTO primeDTO = primeMapper.toDto(prime);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrimeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, primeDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(primeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Prime in the database
        List<Prime> primeList = primeRepository.findAll();
        assertThat(primeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPrime() throws Exception {
        int databaseSizeBeforeUpdate = primeRepository.findAll().size();
        prime.setId(longCount.incrementAndGet());

        // Create the Prime
        PrimeDTO primeDTO = primeMapper.toDto(prime);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrimeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(primeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Prime in the database
        List<Prime> primeList = primeRepository.findAll();
        assertThat(primeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPrime() throws Exception {
        int databaseSizeBeforeUpdate = primeRepository.findAll().size();
        prime.setId(longCount.incrementAndGet());

        // Create the Prime
        PrimeDTO primeDTO = primeMapper.toDto(prime);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrimeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(primeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Prime in the database
        List<Prime> primeList = primeRepository.findAll();
        assertThat(primeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePrime() throws Exception {
        // Initialize the database
        primeRepository.saveAndFlush(prime);

        int databaseSizeBeforeDelete = primeRepository.findAll().size();

        // Delete the prime
        restPrimeMockMvc
            .perform(delete(ENTITY_API_URL_ID, prime.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Prime> primeList = primeRepository.findAll();
        assertThat(primeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
