package fr.it_akademy.test.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import fr.it_akademy.test.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MagasinDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MagasinDTO.class);
        MagasinDTO magasinDTO1 = new MagasinDTO();
        magasinDTO1.setId(1L);
        MagasinDTO magasinDTO2 = new MagasinDTO();
        assertThat(magasinDTO1).isNotEqualTo(magasinDTO2);
        magasinDTO2.setId(magasinDTO1.getId());
        assertThat(magasinDTO1).isEqualTo(magasinDTO2);
        magasinDTO2.setId(2L);
        assertThat(magasinDTO1).isNotEqualTo(magasinDTO2);
        magasinDTO1.setId(null);
        assertThat(magasinDTO1).isNotEqualTo(magasinDTO2);
    }
}
