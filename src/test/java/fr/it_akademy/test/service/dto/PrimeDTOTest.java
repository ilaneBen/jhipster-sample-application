package fr.it_akademy.test.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import fr.it_akademy.test.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PrimeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrimeDTO.class);
        PrimeDTO primeDTO1 = new PrimeDTO();
        primeDTO1.setId(1L);
        PrimeDTO primeDTO2 = new PrimeDTO();
        assertThat(primeDTO1).isNotEqualTo(primeDTO2);
        primeDTO2.setId(primeDTO1.getId());
        assertThat(primeDTO1).isEqualTo(primeDTO2);
        primeDTO2.setId(2L);
        assertThat(primeDTO1).isNotEqualTo(primeDTO2);
        primeDTO1.setId(null);
        assertThat(primeDTO1).isNotEqualTo(primeDTO2);
    }
}
