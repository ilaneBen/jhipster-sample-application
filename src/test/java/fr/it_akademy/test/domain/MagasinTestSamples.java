package fr.it_akademy.test.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class MagasinTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Magasin getMagasinSample1() {
        return new Magasin().id(1L).nom("nom1");
    }

    public static Magasin getMagasinSample2() {
        return new Magasin().id(2L).nom("nom2");
    }

    public static Magasin getMagasinRandomSampleGenerator() {
        return new Magasin().id(longCount.incrementAndGet()).nom(UUID.randomUUID().toString());
    }
}
