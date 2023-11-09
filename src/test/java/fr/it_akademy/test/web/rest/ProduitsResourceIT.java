package fr.it_akademy.test.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.it_akademy.test.IntegrationTest;
import fr.it_akademy.test.domain.Produits;
import fr.it_akademy.test.repository.ProduitsRepository;
import fr.it_akademy.test.service.dto.ProduitsDTO;
import fr.it_akademy.test.service.mapper.ProduitsMapper;
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
 * Integration tests for the {@link ProduitsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProduitsResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final Double DEFAULT_PRIX = 1D;
    private static final Double UPDATED_PRIX = 2D;

    private static final String DEFAULT_PHOTO = "AAAAAAAAAA";
    private static final String UPDATED_PHOTO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/produits";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProduitsRepository produitsRepository;

    @Autowired
    private ProduitsMapper produitsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProduitsMockMvc;

    private Produits produits;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Produits createEntity(EntityManager em) {
        Produits produits = new Produits().nom(DEFAULT_NOM).prix(DEFAULT_PRIX).photo(DEFAULT_PHOTO).description(DEFAULT_DESCRIPTION);
        return produits;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Produits createUpdatedEntity(EntityManager em) {
        Produits produits = new Produits().nom(UPDATED_NOM).prix(UPDATED_PRIX).photo(UPDATED_PHOTO).description(UPDATED_DESCRIPTION);
        return produits;
    }

    @BeforeEach
    public void initTest() {
        produits = createEntity(em);
    }

    @Test
    @Transactional
    void createProduits() throws Exception {
        int databaseSizeBeforeCreate = produitsRepository.findAll().size();
        // Create the Produits
        ProduitsDTO produitsDTO = produitsMapper.toDto(produits);
        restProduitsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(produitsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Produits in the database
        List<Produits> produitsList = produitsRepository.findAll();
        assertThat(produitsList).hasSize(databaseSizeBeforeCreate + 1);
        Produits testProduits = produitsList.get(produitsList.size() - 1);
        assertThat(testProduits.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testProduits.getPrix()).isEqualTo(DEFAULT_PRIX);
        assertThat(testProduits.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testProduits.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createProduitsWithExistingId() throws Exception {
        // Create the Produits with an existing ID
        produits.setId(1L);
        ProduitsDTO produitsDTO = produitsMapper.toDto(produits);

        int databaseSizeBeforeCreate = produitsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProduitsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(produitsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Produits in the database
        List<Produits> produitsList = produitsRepository.findAll();
        assertThat(produitsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = produitsRepository.findAll().size();
        // set the field null
        produits.setNom(null);

        // Create the Produits, which fails.
        ProduitsDTO produitsDTO = produitsMapper.toDto(produits);

        restProduitsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(produitsDTO))
            )
            .andExpect(status().isBadRequest());

        List<Produits> produitsList = produitsRepository.findAll();
        assertThat(produitsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPrixIsRequired() throws Exception {
        int databaseSizeBeforeTest = produitsRepository.findAll().size();
        // set the field null
        produits.setPrix(null);

        // Create the Produits, which fails.
        ProduitsDTO produitsDTO = produitsMapper.toDto(produits);

        restProduitsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(produitsDTO))
            )
            .andExpect(status().isBadRequest());

        List<Produits> produitsList = produitsRepository.findAll();
        assertThat(produitsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllProduits() throws Exception {
        // Initialize the database
        produitsRepository.saveAndFlush(produits);

        // Get all the produitsList
        restProduitsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(produits.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].prix").value(hasItem(DEFAULT_PRIX.doubleValue())))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(DEFAULT_PHOTO)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getProduits() throws Exception {
        // Initialize the database
        produitsRepository.saveAndFlush(produits);

        // Get the produits
        restProduitsMockMvc
            .perform(get(ENTITY_API_URL_ID, produits.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(produits.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.prix").value(DEFAULT_PRIX.doubleValue()))
            .andExpect(jsonPath("$.photo").value(DEFAULT_PHOTO))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingProduits() throws Exception {
        // Get the produits
        restProduitsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProduits() throws Exception {
        // Initialize the database
        produitsRepository.saveAndFlush(produits);

        int databaseSizeBeforeUpdate = produitsRepository.findAll().size();

        // Update the produits
        Produits updatedProduits = produitsRepository.findById(produits.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedProduits are not directly saved in db
        em.detach(updatedProduits);
        updatedProduits.nom(UPDATED_NOM).prix(UPDATED_PRIX).photo(UPDATED_PHOTO).description(UPDATED_DESCRIPTION);
        ProduitsDTO produitsDTO = produitsMapper.toDto(updatedProduits);

        restProduitsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, produitsDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(produitsDTO))
            )
            .andExpect(status().isOk());

        // Validate the Produits in the database
        List<Produits> produitsList = produitsRepository.findAll();
        assertThat(produitsList).hasSize(databaseSizeBeforeUpdate);
        Produits testProduits = produitsList.get(produitsList.size() - 1);
        assertThat(testProduits.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testProduits.getPrix()).isEqualTo(UPDATED_PRIX);
        assertThat(testProduits.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testProduits.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingProduits() throws Exception {
        int databaseSizeBeforeUpdate = produitsRepository.findAll().size();
        produits.setId(longCount.incrementAndGet());

        // Create the Produits
        ProduitsDTO produitsDTO = produitsMapper.toDto(produits);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProduitsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, produitsDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(produitsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Produits in the database
        List<Produits> produitsList = produitsRepository.findAll();
        assertThat(produitsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProduits() throws Exception {
        int databaseSizeBeforeUpdate = produitsRepository.findAll().size();
        produits.setId(longCount.incrementAndGet());

        // Create the Produits
        ProduitsDTO produitsDTO = produitsMapper.toDto(produits);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProduitsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(produitsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Produits in the database
        List<Produits> produitsList = produitsRepository.findAll();
        assertThat(produitsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProduits() throws Exception {
        int databaseSizeBeforeUpdate = produitsRepository.findAll().size();
        produits.setId(longCount.incrementAndGet());

        // Create the Produits
        ProduitsDTO produitsDTO = produitsMapper.toDto(produits);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProduitsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(produitsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Produits in the database
        List<Produits> produitsList = produitsRepository.findAll();
        assertThat(produitsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProduitsWithPatch() throws Exception {
        // Initialize the database
        produitsRepository.saveAndFlush(produits);

        int databaseSizeBeforeUpdate = produitsRepository.findAll().size();

        // Update the produits using partial update
        Produits partialUpdatedProduits = new Produits();
        partialUpdatedProduits.setId(produits.getId());

        partialUpdatedProduits.prix(UPDATED_PRIX).photo(UPDATED_PHOTO);

        restProduitsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProduits.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProduits))
            )
            .andExpect(status().isOk());

        // Validate the Produits in the database
        List<Produits> produitsList = produitsRepository.findAll();
        assertThat(produitsList).hasSize(databaseSizeBeforeUpdate);
        Produits testProduits = produitsList.get(produitsList.size() - 1);
        assertThat(testProduits.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testProduits.getPrix()).isEqualTo(UPDATED_PRIX);
        assertThat(testProduits.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testProduits.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateProduitsWithPatch() throws Exception {
        // Initialize the database
        produitsRepository.saveAndFlush(produits);

        int databaseSizeBeforeUpdate = produitsRepository.findAll().size();

        // Update the produits using partial update
        Produits partialUpdatedProduits = new Produits();
        partialUpdatedProduits.setId(produits.getId());

        partialUpdatedProduits.nom(UPDATED_NOM).prix(UPDATED_PRIX).photo(UPDATED_PHOTO).description(UPDATED_DESCRIPTION);

        restProduitsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProduits.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProduits))
            )
            .andExpect(status().isOk());

        // Validate the Produits in the database
        List<Produits> produitsList = produitsRepository.findAll();
        assertThat(produitsList).hasSize(databaseSizeBeforeUpdate);
        Produits testProduits = produitsList.get(produitsList.size() - 1);
        assertThat(testProduits.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testProduits.getPrix()).isEqualTo(UPDATED_PRIX);
        assertThat(testProduits.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testProduits.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingProduits() throws Exception {
        int databaseSizeBeforeUpdate = produitsRepository.findAll().size();
        produits.setId(longCount.incrementAndGet());

        // Create the Produits
        ProduitsDTO produitsDTO = produitsMapper.toDto(produits);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProduitsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, produitsDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(produitsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Produits in the database
        List<Produits> produitsList = produitsRepository.findAll();
        assertThat(produitsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProduits() throws Exception {
        int databaseSizeBeforeUpdate = produitsRepository.findAll().size();
        produits.setId(longCount.incrementAndGet());

        // Create the Produits
        ProduitsDTO produitsDTO = produitsMapper.toDto(produits);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProduitsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(produitsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Produits in the database
        List<Produits> produitsList = produitsRepository.findAll();
        assertThat(produitsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProduits() throws Exception {
        int databaseSizeBeforeUpdate = produitsRepository.findAll().size();
        produits.setId(longCount.incrementAndGet());

        // Create the Produits
        ProduitsDTO produitsDTO = produitsMapper.toDto(produits);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProduitsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(produitsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Produits in the database
        List<Produits> produitsList = produitsRepository.findAll();
        assertThat(produitsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProduits() throws Exception {
        // Initialize the database
        produitsRepository.saveAndFlush(produits);

        int databaseSizeBeforeDelete = produitsRepository.findAll().size();

        // Delete the produits
        restProduitsMockMvc
            .perform(delete(ENTITY_API_URL_ID, produits.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Produits> produitsList = produitsRepository.findAll();
        assertThat(produitsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
