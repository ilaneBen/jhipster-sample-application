package fr.it_akademy.test.domain;

import static fr.it_akademy.test.domain.ProduitsTestSamples.*;
import static fr.it_akademy.test.domain.VendeurTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import fr.it_akademy.test.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ProduitsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Produits.class);
        Produits produits1 = getProduitsSample1();
        Produits produits2 = new Produits();
        assertThat(produits1).isNotEqualTo(produits2);

        produits2.setId(produits1.getId());
        assertThat(produits1).isEqualTo(produits2);

        produits2 = getProduitsSample2();
        assertThat(produits1).isNotEqualTo(produits2);
    }

    @Test
    void vendeurTest() throws Exception {
        Produits produits = getProduitsRandomSampleGenerator();
        Vendeur vendeurBack = getVendeurRandomSampleGenerator();

        produits.addVendeur(vendeurBack);
        assertThat(produits.getVendeurs()).containsOnly(vendeurBack);
        assertThat(vendeurBack.getProduits()).isEqualTo(produits);

        produits.removeVendeur(vendeurBack);
        assertThat(produits.getVendeurs()).doesNotContain(vendeurBack);
        assertThat(vendeurBack.getProduits()).isNull();

        produits.vendeurs(new HashSet<>(Set.of(vendeurBack)));
        assertThat(produits.getVendeurs()).containsOnly(vendeurBack);
        assertThat(vendeurBack.getProduits()).isEqualTo(produits);

        produits.setVendeurs(new HashSet<>());
        assertThat(produits.getVendeurs()).doesNotContain(vendeurBack);
        assertThat(vendeurBack.getProduits()).isNull();
    }
}
