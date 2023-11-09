package fr.it_akademy.test.domain;

import static fr.it_akademy.test.domain.MagasinTestSamples.*;
import static fr.it_akademy.test.domain.ProduitsTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import fr.it_akademy.test.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class MagasinTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Magasin.class);
        Magasin magasin1 = getMagasinSample1();
        Magasin magasin2 = new Magasin();
        assertThat(magasin1).isNotEqualTo(magasin2);

        magasin2.setId(magasin1.getId());
        assertThat(magasin1).isEqualTo(magasin2);

        magasin2 = getMagasinSample2();
        assertThat(magasin1).isNotEqualTo(magasin2);
    }

    @Test
    void produitsTest() throws Exception {
        Magasin magasin = getMagasinRandomSampleGenerator();
        Produits produitsBack = getProduitsRandomSampleGenerator();

        magasin.addProduits(produitsBack);
        assertThat(magasin.getProduits()).containsOnly(produitsBack);
        assertThat(produitsBack.getMagasin()).isEqualTo(magasin);

        magasin.removeProduits(produitsBack);
        assertThat(magasin.getProduits()).doesNotContain(produitsBack);
        assertThat(produitsBack.getMagasin()).isNull();

        magasin.produits(new HashSet<>(Set.of(produitsBack)));
        assertThat(magasin.getProduits()).containsOnly(produitsBack);
        assertThat(produitsBack.getMagasin()).isEqualTo(magasin);

        magasin.setProduits(new HashSet<>());
        assertThat(magasin.getProduits()).doesNotContain(produitsBack);
        assertThat(produitsBack.getMagasin()).isNull();
    }
}
