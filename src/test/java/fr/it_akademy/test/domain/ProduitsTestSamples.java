package fr.it_akademy.test.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ProduitsTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Produits getProduitsSample1() {
        return new Produits().id(1L).nom("nom1").photo("photo1").description("description1");
    }

    public static Produits getProduitsSample2() {
        return new Produits().id(2L).nom("nom2").photo("photo2").description("description2");
    }

    public static Produits getProduitsRandomSampleGenerator() {
        return new Produits()
            .id(longCount.incrementAndGet())
            .nom(UUID.randomUUID().toString())
            .photo(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString());
    }
}
