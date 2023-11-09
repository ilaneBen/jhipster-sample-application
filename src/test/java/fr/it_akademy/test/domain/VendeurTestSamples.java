package fr.it_akademy.test.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class VendeurTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Vendeur getVendeurSample1() {
        return new Vendeur().id(1L).nom("nom1");
    }

    public static Vendeur getVendeurSample2() {
        return new Vendeur().id(2L).nom("nom2");
    }

    public static Vendeur getVendeurRandomSampleGenerator() {
        return new Vendeur().id(longCount.incrementAndGet()).nom(UUID.randomUUID().toString());
    }
}
