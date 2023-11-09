package fr.it_akademy.test.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import fr.it_akademy.test.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VendeurDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VendeurDTO.class);
        VendeurDTO vendeurDTO1 = new VendeurDTO();
        vendeurDTO1.setId(1L);
        VendeurDTO vendeurDTO2 = new VendeurDTO();
        assertThat(vendeurDTO1).isNotEqualTo(vendeurDTO2);
        vendeurDTO2.setId(vendeurDTO1.getId());
        assertThat(vendeurDTO1).isEqualTo(vendeurDTO2);
        vendeurDTO2.setId(2L);
        assertThat(vendeurDTO1).isNotEqualTo(vendeurDTO2);
        vendeurDTO1.setId(null);
        assertThat(vendeurDTO1).isNotEqualTo(vendeurDTO2);
    }
}
