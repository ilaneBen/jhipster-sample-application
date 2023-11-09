package fr.it_akademy.test.domain;

import static fr.it_akademy.test.domain.PrimeTestSamples.*;
import static fr.it_akademy.test.domain.ProduitsTestSamples.*;
import static fr.it_akademy.test.domain.VendeurTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import fr.it_akademy.test.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class VendeurTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Vendeur.class);
        Vendeur vendeur1 = getVendeurSample1();
        Vendeur vendeur2 = new Vendeur();
        assertThat(vendeur1).isNotEqualTo(vendeur2);

        vendeur2.setId(vendeur1.getId());
        assertThat(vendeur1).isEqualTo(vendeur2);

        vendeur2 = getVendeurSample2();
        assertThat(vendeur1).isNotEqualTo(vendeur2);
    }

    @Test
    void primeTest() throws Exception {
        Vendeur vendeur = getVendeurRandomSampleGenerator();
        Prime primeBack = getPrimeRandomSampleGenerator();

        vendeur.addPrime(primeBack);
        assertThat(vendeur.getPrimes()).containsOnly(primeBack);

        vendeur.removePrime(primeBack);
        assertThat(vendeur.getPrimes()).doesNotContain(primeBack);

        vendeur.primes(new HashSet<>(Set.of(primeBack)));
        assertThat(vendeur.getPrimes()).containsOnly(primeBack);

        vendeur.setPrimes(new HashSet<>());
        assertThat(vendeur.getPrimes()).doesNotContain(primeBack);
    }

    @Test
    void produitsTest() throws Exception {
        Vendeur vendeur = getVendeurRandomSampleGenerator();
        Produits produitsBack = getProduitsRandomSampleGenerator();

        vendeur.setProduits(produitsBack);
        assertThat(vendeur.getProduits()).isEqualTo(produitsBack);

        vendeur.produits(null);
        assertThat(vendeur.getProduits()).isNull();
    }
}
