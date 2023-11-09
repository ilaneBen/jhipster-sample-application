package fr.it_akademy.test.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import fr.it_akademy.test.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProduitsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProduitsDTO.class);
        ProduitsDTO produitsDTO1 = new ProduitsDTO();
        produitsDTO1.setId(1L);
        ProduitsDTO produitsDTO2 = new ProduitsDTO();
        assertThat(produitsDTO1).isNotEqualTo(produitsDTO2);
        produitsDTO2.setId(produitsDTO1.getId());
        assertThat(produitsDTO1).isEqualTo(produitsDTO2);
        produitsDTO2.setId(2L);
        assertThat(produitsDTO1).isNotEqualTo(produitsDTO2);
        produitsDTO1.setId(null);
        assertThat(produitsDTO1).isNotEqualTo(produitsDTO2);
    }
}
