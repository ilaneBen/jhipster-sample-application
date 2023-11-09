package fr.it_akademy.test.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.it_akademy.test.IntegrationTest;
import fr.it_akademy.test.domain.Vendeur;
import fr.it_akademy.test.repository.VendeurRepository;
import fr.it_akademy.test.service.VendeurService;
import fr.it_akademy.test.service.dto.VendeurDTO;
import fr.it_akademy.test.service.mapper.VendeurMapper;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link VendeurResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class VendeurResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final Double DEFAULT_NBR_VENDU = 1D;
    private static final Double UPDATED_NBR_VENDU = 2D;

    private static final Boolean DEFAULT_OBJECTIF_ATTEINT = false;
    private static final Boolean UPDATED_OBJECTIF_ATTEINT = true;

    private static final String ENTITY_API_URL = "/api/vendeurs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private VendeurRepository vendeurRepository;

    @Mock
    private VendeurRepository vendeurRepositoryMock;

    @Autowired
    private VendeurMapper vendeurMapper;

    @Mock
    private VendeurService vendeurServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVendeurMockMvc;

    private Vendeur vendeur;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vendeur createEntity(EntityManager em) {
        Vendeur vendeur = new Vendeur().nom(DEFAULT_NOM).nbrVendu(DEFAULT_NBR_VENDU).objectifAtteint(DEFAULT_OBJECTIF_ATTEINT);
        return vendeur;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vendeur createUpdatedEntity(EntityManager em) {
        Vendeur vendeur = new Vendeur().nom(UPDATED_NOM).nbrVendu(UPDATED_NBR_VENDU).objectifAtteint(UPDATED_OBJECTIF_ATTEINT);
        return vendeur;
    }

    @BeforeEach
    public void initTest() {
        vendeur = createEntity(em);
    }

    @Test
    @Transactional
    void createVendeur() throws Exception {
        int databaseSizeBeforeCreate = vendeurRepository.findAll().size();
        // Create the Vendeur
        VendeurDTO vendeurDTO = vendeurMapper.toDto(vendeur);
        restVendeurMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vendeurDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Vendeur in the database
        List<Vendeur> vendeurList = vendeurRepository.findAll();
        assertThat(vendeurList).hasSize(databaseSizeBeforeCreate + 1);
        Vendeur testVendeur = vendeurList.get(vendeurList.size() - 1);
        assertThat(testVendeur.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testVendeur.getNbrVendu()).isEqualTo(DEFAULT_NBR_VENDU);
        assertThat(testVendeur.getObjectifAtteint()).isEqualTo(DEFAULT_OBJECTIF_ATTEINT);
    }

    @Test
    @Transactional
    void createVendeurWithExistingId() throws Exception {
        // Create the Vendeur with an existing ID
        vendeur.setId(1L);
        VendeurDTO vendeurDTO = vendeurMapper.toDto(vendeur);

        int databaseSizeBeforeCreate = vendeurRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVendeurMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vendeurDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vendeur in the database
        List<Vendeur> vendeurList = vendeurRepository.findAll();
        assertThat(vendeurList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = vendeurRepository.findAll().size();
        // set the field null
        vendeur.setNom(null);

        // Create the Vendeur, which fails.
        VendeurDTO vendeurDTO = vendeurMapper.toDto(vendeur);

        restVendeurMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vendeurDTO))
            )
            .andExpect(status().isBadRequest());

        List<Vendeur> vendeurList = vendeurRepository.findAll();
        assertThat(vendeurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNbrVenduIsRequired() throws Exception {
        int databaseSizeBeforeTest = vendeurRepository.findAll().size();
        // set the field null
        vendeur.setNbrVendu(null);

        // Create the Vendeur, which fails.
        VendeurDTO vendeurDTO = vendeurMapper.toDto(vendeur);

        restVendeurMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vendeurDTO))
            )
            .andExpect(status().isBadRequest());

        List<Vendeur> vendeurList = vendeurRepository.findAll();
        assertThat(vendeurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllVendeurs() throws Exception {
        // Initialize the database
        vendeurRepository.saveAndFlush(vendeur);

        // Get all the vendeurList
        restVendeurMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vendeur.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].nbrVendu").value(hasItem(DEFAULT_NBR_VENDU.doubleValue())))
            .andExpect(jsonPath("$.[*].objectifAtteint").value(hasItem(DEFAULT_OBJECTIF_ATTEINT.booleanValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllVendeursWithEagerRelationshipsIsEnabled() throws Exception {
        when(vendeurServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restVendeurMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(vendeurServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllVendeursWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(vendeurServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restVendeurMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(vendeurRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getVendeur() throws Exception {
        // Initialize the database
        vendeurRepository.saveAndFlush(vendeur);

        // Get the vendeur
        restVendeurMockMvc
            .perform(get(ENTITY_API_URL_ID, vendeur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vendeur.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.nbrVendu").value(DEFAULT_NBR_VENDU.doubleValue()))
            .andExpect(jsonPath("$.objectifAtteint").value(DEFAULT_OBJECTIF_ATTEINT.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingVendeur() throws Exception {
        // Get the vendeur
        restVendeurMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVendeur() throws Exception {
        // Initialize the database
        vendeurRepository.saveAndFlush(vendeur);

        int databaseSizeBeforeUpdate = vendeurRepository.findAll().size();

        // Update the vendeur
        Vendeur updatedVendeur = vendeurRepository.findById(vendeur.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedVendeur are not directly saved in db
        em.detach(updatedVendeur);
        updatedVendeur.nom(UPDATED_NOM).nbrVendu(UPDATED_NBR_VENDU).objectifAtteint(UPDATED_OBJECTIF_ATTEINT);
        VendeurDTO vendeurDTO = vendeurMapper.toDto(updatedVendeur);

        restVendeurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vendeurDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vendeurDTO))
            )
            .andExpect(status().isOk());

        // Validate the Vendeur in the database
        List<Vendeur> vendeurList = vendeurRepository.findAll();
        assertThat(vendeurList).hasSize(databaseSizeBeforeUpdate);
        Vendeur testVendeur = vendeurList.get(vendeurList.size() - 1);
        assertThat(testVendeur.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testVendeur.getNbrVendu()).isEqualTo(UPDATED_NBR_VENDU);
        assertThat(testVendeur.getObjectifAtteint()).isEqualTo(UPDATED_OBJECTIF_ATTEINT);
    }

    @Test
    @Transactional
    void putNonExistingVendeur() throws Exception {
        int databaseSizeBeforeUpdate = vendeurRepository.findAll().size();
        vendeur.setId(longCount.incrementAndGet());

        // Create the Vendeur
        VendeurDTO vendeurDTO = vendeurMapper.toDto(vendeur);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVendeurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vendeurDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vendeurDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vendeur in the database
        List<Vendeur> vendeurList = vendeurRepository.findAll();
        assertThat(vendeurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVendeur() throws Exception {
        int databaseSizeBeforeUpdate = vendeurRepository.findAll().size();
        vendeur.setId(longCount.incrementAndGet());

        // Create the Vendeur
        VendeurDTO vendeurDTO = vendeurMapper.toDto(vendeur);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVendeurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vendeurDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vendeur in the database
        List<Vendeur> vendeurList = vendeurRepository.findAll();
        assertThat(vendeurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVendeur() throws Exception {
        int databaseSizeBeforeUpdate = vendeurRepository.findAll().size();
        vendeur.setId(longCount.incrementAndGet());

        // Create the Vendeur
        VendeurDTO vendeurDTO = vendeurMapper.toDto(vendeur);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVendeurMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vendeurDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Vendeur in the database
        List<Vendeur> vendeurList = vendeurRepository.findAll();
        assertThat(vendeurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVendeurWithPatch() throws Exception {
        // Initialize the database
        vendeurRepository.saveAndFlush(vendeur);

        int databaseSizeBeforeUpdate = vendeurRepository.findAll().size();

        // Update the vendeur using partial update
        Vendeur partialUpdatedVendeur = new Vendeur();
        partialUpdatedVendeur.setId(vendeur.getId());

        partialUpdatedVendeur.objectifAtteint(UPDATED_OBJECTIF_ATTEINT);

        restVendeurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVendeur.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVendeur))
            )
            .andExpect(status().isOk());

        // Validate the Vendeur in the database
        List<Vendeur> vendeurList = vendeurRepository.findAll();
        assertThat(vendeurList).hasSize(databaseSizeBeforeUpdate);
        Vendeur testVendeur = vendeurList.get(vendeurList.size() - 1);
        assertThat(testVendeur.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testVendeur.getNbrVendu()).isEqualTo(DEFAULT_NBR_VENDU);
        assertThat(testVendeur.getObjectifAtteint()).isEqualTo(UPDATED_OBJECTIF_ATTEINT);
    }

    @Test
    @Transactional
    void fullUpdateVendeurWithPatch() throws Exception {
        // Initialize the database
        vendeurRepository.saveAndFlush(vendeur);

        int databaseSizeBeforeUpdate = vendeurRepository.findAll().size();

        // Update the vendeur using partial update
        Vendeur partialUpdatedVendeur = new Vendeur();
        partialUpdatedVendeur.setId(vendeur.getId());

        partialUpdatedVendeur.nom(UPDATED_NOM).nbrVendu(UPDATED_NBR_VENDU).objectifAtteint(UPDATED_OBJECTIF_ATTEINT);

        restVendeurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVendeur.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVendeur))
            )
            .andExpect(status().isOk());

        // Validate the Vendeur in the database
        List<Vendeur> vendeurList = vendeurRepository.findAll();
        assertThat(vendeurList).hasSize(databaseSizeBeforeUpdate);
        Vendeur testVendeur = vendeurList.get(vendeurList.size() - 1);
        assertThat(testVendeur.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testVendeur.getNbrVendu()).isEqualTo(UPDATED_NBR_VENDU);
        assertThat(testVendeur.getObjectifAtteint()).isEqualTo(UPDATED_OBJECTIF_ATTEINT);
    }

    @Test
    @Transactional
    void patchNonExistingVendeur() throws Exception {
        int databaseSizeBeforeUpdate = vendeurRepository.findAll().size();
        vendeur.setId(longCount.incrementAndGet());

        // Create the Vendeur
        VendeurDTO vendeurDTO = vendeurMapper.toDto(vendeur);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVendeurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, vendeurDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vendeurDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vendeur in the database
        List<Vendeur> vendeurList = vendeurRepository.findAll();
        assertThat(vendeurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVendeur() throws Exception {
        int databaseSizeBeforeUpdate = vendeurRepository.findAll().size();
        vendeur.setId(longCount.incrementAndGet());

        // Create the Vendeur
        VendeurDTO vendeurDTO = vendeurMapper.toDto(vendeur);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVendeurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vendeurDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vendeur in the database
        List<Vendeur> vendeurList = vendeurRepository.findAll();
        assertThat(vendeurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVendeur() throws Exception {
        int databaseSizeBeforeUpdate = vendeurRepository.findAll().size();
        vendeur.setId(longCount.incrementAndGet());

        // Create the Vendeur
        VendeurDTO vendeurDTO = vendeurMapper.toDto(vendeur);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVendeurMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vendeurDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Vendeur in the database
        List<Vendeur> vendeurList = vendeurRepository.findAll();
        assertThat(vendeurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVendeur() throws Exception {
        // Initialize the database
        vendeurRepository.saveAndFlush(vendeur);

        int databaseSizeBeforeDelete = vendeurRepository.findAll().size();

        // Delete the vendeur
        restVendeurMockMvc
            .perform(delete(ENTITY_API_URL_ID, vendeur.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Vendeur> vendeurList = vendeurRepository.findAll();
        assertThat(vendeurList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
