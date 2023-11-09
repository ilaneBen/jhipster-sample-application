package fr.it_akademy.test.domain;

import static fr.it_akademy.test.domain.PrimeTestSamples.*;
import static fr.it_akademy.test.domain.VendeurTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import fr.it_akademy.test.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class PrimeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Prime.class);
        Prime prime1 = getPrimeSample1();
        Prime prime2 = new Prime();
        assertThat(prime1).isNotEqualTo(prime2);

        prime2.setId(prime1.getId());
        assertThat(prime1).isEqualTo(prime2);

        prime2 = getPrimeSample2();
        assertThat(prime1).isNotEqualTo(prime2);
    }

    @Test
    void vendeurTest() throws Exception {
        Prime prime = getPrimeRandomSampleGenerator();
        Vendeur vendeurBack = getVendeurRandomSampleGenerator();

        prime.addVendeur(vendeurBack);
        assertThat(prime.getVendeurs()).containsOnly(vendeurBack);
        assertThat(vendeurBack.getPrimes()).containsOnly(prime);

        prime.removeVendeur(vendeurBack);
        assertThat(prime.getVendeurs()).doesNotContain(vendeurBack);
        assertThat(vendeurBack.getPrimes()).doesNotContain(prime);

        prime.vendeurs(new HashSet<>(Set.of(vendeurBack)));
        assertThat(prime.getVendeurs()).containsOnly(vendeurBack);
        assertThat(vendeurBack.getPrimes()).containsOnly(prime);

        prime.setVendeurs(new HashSet<>());
        assertThat(prime.getVendeurs()).doesNotContain(vendeurBack);
        assertThat(vendeurBack.getPrimes()).doesNotContain(prime);
    }
}
